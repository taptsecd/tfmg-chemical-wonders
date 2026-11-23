package dev.taptsecd.chemicalwonders;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;

public final class ChemicalItems {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ChemicalWonders.MOD_ID);
    private static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ChemicalWonders.MOD_ID);

    public static final DeferredItem<ChemicalMaterialItem> SODIUM_CHLORIDE = ITEMS.register("sodium_chloride",
            () -> new ChemicalMaterialItem(new Item.Properties(),
                    dev.taptsecd.chemicalwonders.chemistry.Substances.SODIUM_CHLORIDE));

    public static final DeferredItem<ChemicalMaterialItem> SODIUM_HYDROXIDE = ITEMS.register("sodium_hydroxide",
            () -> new ChemicalMaterialItem(new Item.Properties(),
                    dev.taptsecd.chemicalwonders.chemistry.Substances.SODIUM_HYDROXIDE));

    static {
        TABS.register("chemistry", () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.tfmg_chemical_wonders.chemistry"))
                .icon(() -> new ItemStack(SODIUM_CHLORIDE.get()))
                .displayItems((parameters, output) -> {
                    output.accept(SODIUM_CHLORIDE.get());
                    output.accept(SODIUM_HYDROXIDE.get());
                    output.accept(ChemicalFluids.SODIUM_HYDROXIDE_SOLUTION.bucket.get());
                    output.accept(ChemicalFluids.SEAWATER.bucket.get());
                    output.accept(ChemicalFluids.BRINE.bucket.get());
                    output.accept(SaltDeposits.ROCK_SALT_ITEM.get());
                })
                .build());
    }

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
        TABS.register(bus);
    }

    private ChemicalItems() {}
}
