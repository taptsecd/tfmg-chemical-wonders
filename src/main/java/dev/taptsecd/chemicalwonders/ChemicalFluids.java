package dev.taptsecd.chemicalwonders;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class ChemicalFluids {
    private static final DeferredRegister<FluidType> TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, ChemicalWonders.MOD_ID);
    private static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(Registries.FLUID, ChemicalWonders.MOD_ID);
    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ChemicalWonders.MOD_ID);
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ChemicalWonders.MOD_ID);

    public static final Solution SEAWATER = new Solution("seawater");
    public static final Solution BRINE = new Solution("brine");

    public static final Solution SODIUM_HYDROXIDE_SOLUTION = new Solution("sodium_hydroxide_solution");
    public static final Gas CHLORINE = new Gas("chlorine");

    /** Tank-only gas: intentionally no bucket or placeable block. */
    public static final class Gas {
        public final DeferredHolder<FluidType, FluidType> type;
        public final DeferredHolder<Fluid, BaseFlowingFluid.Source> source;
        public final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> flowing;

        private Gas(String name) {
            type = TYPES.register(name, () -> new FluidType(FluidType.Properties.create()
                    .density(3).viscosity(100).canSwim(false).canConvertToSource(false)) {});
            source = FLUIDS.register(name, () -> new BaseFlowingFluid.Source(properties()));
            flowing = FLUIDS.register("flowing_" + name, () -> new BaseFlowingFluid.Flowing(properties()));
        }

        private BaseFlowingFluid.Properties properties() {
            return new BaseFlowingFluid.Properties(type, source, flowing);
        }
    }

    public static final class Solution {
        public final DeferredHolder<FluidType, FluidType> type;
        public final DeferredHolder<Fluid, BaseFlowingFluid.Source> source;
        public final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> flowing;
        public final DeferredItem<BucketItem> bucket;
        private final net.neoforged.neoforge.registries.DeferredBlock<LiquidBlock> block;

        private Solution(String name) {
            type = TYPES.register(name, () -> new FluidType(FluidType.Properties.create()
                    .density(1030).viscosity(1000).canConvertToSource(false)) {});
            source = FLUIDS.register(name, () -> new BaseFlowingFluid.Source(properties()));
            flowing = FLUIDS.register("flowing_" + name, () -> new BaseFlowingFluid.Flowing(properties()));
            block = BLOCKS.register(name, () -> new LiquidBlock(source.get(),
                    BlockBehaviour.Properties.of().noCollission().strength(100).noLootTable().liquid()));
            bucket = ITEMS.register(name + "_bucket", () -> new BucketItem(source.get(),
                    new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
        }

        private BaseFlowingFluid.Properties properties() {
            return new BaseFlowingFluid.Properties(type, source, flowing).bucket(bucket).block(block)
                    .slopeFindDistance(4).levelDecreasePerBlock(1).tickRate(5);
        }
    }

    public static void register(IEventBus bus) {
        TYPES.register(bus);
        FLUIDS.register(bus);
        BLOCKS.register(bus);
        ITEMS.register(bus);
    }

    private ChemicalFluids() {}
}
