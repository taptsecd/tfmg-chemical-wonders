package dev.taptsecd.chemicalwonders;

import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.IEventBus;

@Mod(ChemicalWonders.MOD_ID)
public final class ChemicalWonders {
    public static final String MOD_ID = "tfmg_chemical_wonders";

    public ChemicalWonders(IEventBus modBus) {
        ChemicalItems.register(modBus);
        ChemicalFluids.register(modBus);
        SaltDeposits.register(modBus);
    }
}
