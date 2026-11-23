package dev.taptsecd.chemicalwonders;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = ChemicalWonders.MOD_ID, value = Dist.CLIENT)
public final class ChemicalClient {
    @SubscribeEvent
    public static void fluidExtensions(RegisterClientExtensionsEvent event) {
        register(event, ChemicalFluids.SEAWATER, 0xff509caf);
        register(event, ChemicalFluids.BRINE, 0xffb5d7ca);
        register(event, ChemicalFluids.SODIUM_HYDROXIDE_SOLUTION, 0xffe0eee7);
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return ResourceLocation.fromNamespaceAndPath("tfmg", "fluid/gas_texture");
            }
            @Override
            public ResourceLocation getFlowingTexture() { return getStillTexture(); }
            @Override
            public int getTintColor() { return 0xffbacb52; }
        }, ChemicalFluids.CHLORINE.type.get());
    }

    private static void register(RegisterClientExtensionsEvent event, ChemicalFluids.Solution solution, int tint) {
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() { return ResourceLocation.withDefaultNamespace("block/water_still"); }
            @Override
            public ResourceLocation getFlowingTexture() { return ResourceLocation.withDefaultNamespace("block/water_flow"); }
            @Override
            public int getTintColor() { return tint; }
        }, solution.type.get());
    }

    @SubscribeEvent
    public static void bucketColors(net.neoforged.neoforge.client.event.RegisterColorHandlersEvent.Item event) {
        event.register(new net.neoforged.neoforge.client.model.DynamicFluidContainerModel.Colors(),
                ChemicalFluids.SEAWATER.bucket.get(), ChemicalFluids.BRINE.bucket.get(),
                ChemicalFluids.SODIUM_HYDROXIDE_SOLUTION.bucket.get());
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            for (var solution : new ChemicalFluids.Solution[]{ChemicalFluids.SEAWATER, ChemicalFluids.BRINE, ChemicalFluids.SODIUM_HYDROXIDE_SOLUTION}) {
                ItemBlockRenderTypes.setRenderLayer(solution.source.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(solution.flowing.get(), RenderType.translucent());
            }
        });
    }
}
