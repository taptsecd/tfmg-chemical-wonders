package dev.taptsecd.chemicalwonders;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class SaltDeposits {
    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ChemicalWonders.MOD_ID);
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ChemicalWonders.MOD_ID);

    public static final DeferredBlock<Block> ROCK_SALT = BLOCKS.registerSimpleBlock("rock_salt",
            BlockBehaviour.Properties.of().strength(1.5f).sound(SoundType.CALCITE).requiresCorrectToolForDrops());
    public static final DeferredItem<BlockItem> ROCK_SALT_ITEM = ITEMS.register("rock_salt",
            () -> new BlockItem(ROCK_SALT.get(), new Item.Properties()));

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
        ITEMS.register(bus);
    }

    private SaltDeposits() {}
}
