package dev.taptsecd.chemicalwonders.testing;

import com.drmangotea.tfmg.recipes.VatMachineRecipe;
import dev.taptsecd.chemicalwonders.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(ChemicalWonders.MOD_ID)
@PrefixGameTestTemplate(false)
public final class ProcessingGameTests {
    @GameTest(template = "salt_test_empty")
    public static void brineElectrolysis(GameTestHelper helper) {
        var recipe = vat(helper, "brine_electrolysis");
        helper.assertTrue(recipe.validate().isEmpty(), "Electrolysis is a valid TFMG processing recipe");
        helper.assertTrue(recipe.machines.size() == 2 && recipe.machines.stream()
                .allMatch(operation -> operation.id().equals(ResourceLocation.parse("tfmg:electrode"))),
                "Electrolysis requires two electrodes, not mixing or arc-furnace operations");
        helper.assertTrue(recipe.allowedVatTypes.size() == 2 && recipe.allowedVatTypes.stream()
                .noneMatch(type -> type.type().getPath().equals("cast_iron")), "Steel or fireproof vat required");
        helper.assertTrue(recipe.getFluidIngredients().size() == 1 && recipe.getFluidIngredients().getFirst()
                .test(new FluidStack(ChemicalFluids.BRINE.source.get(), 500)), "Electrolysis consumes brine");
        helper.assertTrue(!recipe.getFluidIngredients().getFirst().test(new FluidStack(net.minecraft.world.level.material.Fluids.WATER, 500)),
                "Fresh water is not brine");
        helper.assertTrue(recipe.getFluidIngredients().getFirst().amount() == 500, "Batch consumes 500 mB brine");
        helper.assertTrue(recipe.getRollableResults().isEmpty(), "No sodium metal is produced from aqueous brine");
        helper.assertTrue(recipe.getFluidResults().size() == 3, "All three products are retained");
        result(helper, recipe, "tfmg_chemical_wonders:sodium_hydroxide_solution", 500);
        result(helper, recipe, "tfmg_chemical_wonders:chlorine", 250);
        result(helper, recipe, "tfmg:hydrogen", 250);
        helper.succeed();
    }

    @GameTest(template = "salt_test_empty")
    public static void hydroxideProcessing(GameTestHelper helper) {
        var crystallize = vat(helper, "crystallize_sodium_hydroxide");
        var dissolve = vat(helper, "dissolve_sodium_hydroxide");
        helper.assertTrue(crystallize.heatLevel == 1, "Crystallization requires heat");
        helper.assertTrue(crystallize.getFluidIngredients().getFirst().amount() == 250, "Crystallization batch");
        var crystals = crystallize.getRollableResultsAsItemStacks();
        helper.assertTrue(crystals.size() == 1 && crystals.getFirst().is(ChemicalItems.SODIUM_HYDROXIDE.get())
                && crystals.getFirst().getCount() == 1, "One solid hydroxide per batch");
        helper.assertTrue(dissolve.getIngredients().getFirst().test(ChemicalItems.SODIUM_HYDROXIDE.toStack()),
                "Dissolution uses solid hydroxide");
        helper.assertTrue(dissolve.getFluidIngredients().getFirst().amount() == 250, "Dissolution needs solvent");
        result(helper, dissolve, "tfmg_chemical_wonders:sodium_hydroxide_solution", 250);
        helper.assertTrue(ChemicalItems.SODIUM_HYDROXIDE.get().substance()
                .equals(dev.taptsecd.chemicalwonders.chemistry.Substances.SODIUM_HYDROXIDE), "Solid has chemical identity");
        helper.succeed();
    }

    @GameTest(template = "salt_test_empty")
    public static void chlorineIsTankOnly(GameTestHelper helper) {
        helper.assertTrue(ChemicalFluids.CHLORINE.source.get().getBucket() == Items.AIR,
                "Chlorine has no ordinary bucket");
        helper.assertTrue(ChemicalFluids.CHLORINE.source.get().defaultFluidState().createLegacyBlock().is(Blocks.AIR),
                "Chlorine has no placeable liquid block");
        helper.assertTrue(BuiltInRegistries.FLUID.containsKey(ResourceLocation.parse("tfmg:hydrogen")),
                "Reuse TFMG hydrogen rather than introducing a duplicate");
        helper.succeed();
    }

    private static VatMachineRecipe vat(GameTestHelper helper, String path) {
        var id = ResourceLocation.fromNamespaceAndPath(ChemicalWonders.MOD_ID, "vat/" + path);
        var recipe = helper.getLevel().getRecipeManager().byKey(id).orElseThrow().value();
        helper.assertTrue(recipe instanceof VatMachineRecipe, "Loaded TFMG vat recipe: " + path);
        return (VatMachineRecipe) recipe;
    }

    private static void result(GameTestHelper helper, VatMachineRecipe recipe, String fluid, int amount) {
        var id = ResourceLocation.parse(fluid);
        helper.assertTrue(recipe.getFluidResults().stream().anyMatch(stack ->
                BuiltInRegistries.FLUID.getKey(stack.getFluid()).equals(id) && stack.getAmount() == amount),
                "Expected output " + fluid + " x " + amount);
    }
}
