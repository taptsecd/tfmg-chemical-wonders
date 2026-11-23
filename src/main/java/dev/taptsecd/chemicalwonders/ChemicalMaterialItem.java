package dev.taptsecd.chemicalwonders;

import dev.taptsecd.chemicalwonders.chemistry.Substance;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import java.util.List;

/** Connects an obtainable material to the chemistry model without inferring processing recipes. */
public final class ChemicalMaterialItem extends Item {
    private final Substance substance;

    public ChemicalMaterialItem(Properties properties, Substance substance) {
        super(properties);
        this.substance = java.util.Objects.requireNonNull(substance);
    }

    public Substance substance() { return substance; }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip." + ChemicalWonders.MOD_ID + "." + substance.id()));
    }
}
