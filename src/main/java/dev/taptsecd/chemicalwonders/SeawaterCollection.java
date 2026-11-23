package dev.taptsecd.chemicalwonders;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

/** Vanilla ocean water is classified when collected; inland water stays fresh. */
@EventBusSubscriber(modid = ChemicalWonders.MOD_ID)
public final class SeawaterCollection {
    @SubscribeEvent
    public static void collect(PlayerInteractEvent.RightClickItem event) {
        var player = event.getEntity();
        var level = event.getLevel();
        var held = event.getItemStack();
        if (!held.is(Items.BUCKET)) return;
        var start = player.getEyePosition();
        var end = start.add(player.getViewVector(1).scale(player.blockInteractionRange()));
        var hit = level.clip(new ClipContext(start, end, ClipContext.Block.OUTLINE,
                ClipContext.Fluid.SOURCE_ONLY, player));
        if (hit.getType() != HitResult.Type.BLOCK) return;
        var pos = hit.getBlockPos();
        if (!isSeawaterSource(level.getBiome(pos), level.getBlockState(pos))) return;
        if (!level.mayInteract(player, pos) || !player.mayUseItemAt(pos.relative(hit.getDirection()), hit.getDirection(), held)) return;
        var state = level.getBlockState(pos);
        // Do not drain waterlogged blocks: the first implementation collects open water only.
        if (!(state.getBlock() instanceof net.minecraft.world.level.block.LiquidBlock)
                || !(state.getBlock() instanceof BucketPickup pickup)) return;
        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
        if (level.isClientSide) return;
        if (pickup.pickupBlock(player, level, pos, state).isEmpty()) return;
        player.setItemInHand(event.getHand(), ItemUtils.createFilledResult(held, player,
                ChemicalFluids.SEAWATER.bucket.toStack()));
        player.awardStat(Stats.ITEM_USED.get(Items.BUCKET));
        level.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.PLAYERS);
        level.gameEvent(player, GameEvent.FLUID_PICKUP, pos);
    }

    public static boolean isSeawaterSource(net.minecraft.core.Holder<net.minecraft.world.level.biome.Biome> biome,
                                          net.minecraft.world.level.block.state.BlockState state) {
        return biome.is(ChemicalBiomeTags.SALINE_WATER)
                && state.getBlock() instanceof net.minecraft.world.level.block.LiquidBlock
                && state.getFluidState().getType() == Fluids.WATER;
    }

    private SeawaterCollection() {}
}
