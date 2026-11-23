package dev.taptsecd.chemicalwonders.testing;

import dev.taptsecd.chemicalwonders.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

/** These run only in development game-test environments. */
@GameTestHolder(ChemicalWonders.MOD_ID)
@PrefixGameTestTemplate(false)
public final class SaltGameTests {
    @GameTest(template = "salt_test_empty")
    public static void recipesAndFluidsLoad(GameTestHelper helper) {
        var level = helper.getLevel();
        for (String path : new String[]{"salt_from_rock_salt", "crushing/rock_salt",
                "vat/concentrate_seawater", "vat/crystallize_salt", "vat/dissolve_salt", "distillation/seawater"}) {
            helper.assertTrue(level.getRecipeManager().byKey(id(path)).isPresent(), "Recipe failed to load: " + path);
        }
        for (var solution : new ChemicalFluids.Solution[]{ChemicalFluids.SEAWATER, ChemicalFluids.BRINE, ChemicalFluids.SODIUM_HYDROXIDE_SOLUTION}) {
            helper.assertTrue(solution.source.get().getBucket() == solution.bucket.get(), "Fluid bucket registered");
            helper.assertTrue(solution.bucket.get().getCraftingRemainingItem() == Items.BUCKET, "Bucket is returned");
            helper.assertTrue(!solution.type.get().canConvertToSource(solution.source.get().defaultFluidState(),
                    level, helper.absolutePos(BlockPos.ZERO)), "Solutions cannot generate infinite source blocks");
        }
        helper.succeed();
    }

    @GameTest(template = "salt_test_empty")
    public static void depositIsBulkSalt(GameTestHelper helper) {
        var level = helper.getLevel();
        var origin = helper.absolutePos(new BlockPos(10, 4, 10));
        for (var pos : BlockPos.betweenClosed(origin.offset(-8, -3, -8), origin.offset(8, 3, 8))) {
            level.setBlockAndUpdate(pos, Blocks.SANDSTONE.defaultBlockState());
        }
        var feature = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).get(id("rock_salt_deposit"));
        helper.assertTrue(feature != null, "Deposit feature registered");
        feature.place(level, level.getChunkSource().getGenerator(), level.random, origin);
        int saltBlocks = 0;
        for (var pos : BlockPos.betweenClosed(origin.offset(-8, -3, -8), origin.offset(8, 3, 8))) {
            if (level.getBlockState(pos).is(SaltDeposits.ROCK_SALT.get())) saltBlocks++;
        }
        helper.assertTrue(saltBlocks >= 100, "Deposit should form a solid salt lens, got " + saltBlocks);
        helper.succeed();
    }

    @GameTest(template = "salt_test_empty")
    public static void seawaterCollectionRules(GameTestHelper helper) {
        var biomes = helper.getLevel().registryAccess().registryOrThrow(Registries.BIOME);
        var ocean = biomes.getHolderOrThrow(net.minecraft.world.level.biome.Biomes.OCEAN);
        var beach = biomes.getHolderOrThrow(net.minecraft.world.level.biome.Biomes.BEACH);
        var river = biomes.getHolderOrThrow(net.minecraft.world.level.biome.Biomes.RIVER);
        var water = Blocks.WATER.defaultBlockState();
        helper.assertTrue(SeawaterCollection.isSeawaterSource(ocean, water), "Ocean source is saline");
        helper.assertTrue(SeawaterCollection.isSeawaterSource(beach, water), "Beach source is saline");
        helper.assertTrue(!SeawaterCollection.isSeawaterSource(river, water), "River source stays fresh");
        helper.assertTrue(!SeawaterCollection.isSeawaterSource(ocean,
                water.setValue(net.minecraft.world.level.block.LiquidBlock.LEVEL, 1)), "Flowing water cannot be collected");
        helper.assertTrue(!SeawaterCollection.isSeawaterSource(ocean,
                Blocks.OAK_SLAB.defaultBlockState().setValue(net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED, true)),
                "Waterlogged blocks cannot be drained as seawater");
        helper.assertTrue(ChemicalItems.SODIUM_CHLORIDE.get().substance()
                .equals(dev.taptsecd.chemicalwonders.chemistry.Substances.SODIUM_CHLORIDE), "Item retains its chemical identity");
        helper.succeed();
    }

    @GameTest(template = "salt_test_empty")
    public static void bwgCoastalCompatibility(GameTestHelper helper) {
        if (!net.neoforged.fml.ModList.get().isLoaded("biomeswevegone")) {
            // Optional tags must also load successfully without the other mod.
            helper.succeed();
            return;
        }
        var level = helper.getLevel();
        var biomes = level.registryAccess().registryOrThrow(Registries.BIOME);
        for (String path : new String[]{"basalt_barrera", "dacite_shore", "rainbow_beach", "dead_sea", "lush_stacks"}) {
            var key = net.minecraft.resources.ResourceKey.create(Registries.BIOME, bwgId(path));
            var biome = biomes.getHolderOrThrow(key);
            helper.assertTrue(SeawaterCollection.isSeawaterSource(biome, Blocks.WATER.defaultBlockState()),
                    "BWG seawater support: " + path);
            if (!path.equals("dead_sea") && !path.equals("lush_stacks")) {
                helper.assertTrue(biome.is(ChemicalBiomeTags.HAS_ROCK_SALT), "BWG coastal deposits: " + path);
            }
        }
        var inland = biomes.getHolderOrThrow(net.minecraft.resources.ResourceKey.create(Registries.BIOME,
                bwgId("white_mangrove_marshes")));
        helper.assertTrue(!SeawaterCollection.isSeawaterSource(inland, Blocks.WATER.defaultBlockState()),
                "BWG inland marsh water stays fresh");
        var configured = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).get(id("rock_salt_deposit"));
        var config = (net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration) configured.config();
        var pos = helper.absolutePos(new BlockPos(1, 1, 1));
        for (String path : new String[]{"black_sand", "blue_sand", "pink_sand", "purple_sand", "white_sand",
                "white_dacite", "white_dacite_cobblestone"}) {
            var block = net.minecraft.core.registries.BuiltInRegistries.BLOCK.get(bwgId(path));
            helper.assertTrue(block != Blocks.AIR, "BWG substrate exists: " + path);
            level.setBlockAndUpdate(pos, block.defaultBlockState());
            helper.assertTrue(config.target().test(level, pos), "Salt can replace BWG coast material: " + path);
        }
        level.setBlockAndUpdate(pos, Blocks.BASALT.defaultBlockState());
        helper.assertTrue(config.target().test(level, pos), "Salt can replace Basalt Barrera substrate");
        helper.succeed();
    }

    private static ResourceLocation bwgId(String path) {
        return ResourceLocation.fromNamespaceAndPath("biomeswevegone", path);
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(ChemicalWonders.MOD_ID, path);
    }
}
