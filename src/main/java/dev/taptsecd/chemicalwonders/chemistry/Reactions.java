package dev.taptsecd.chemicalwonders.chemistry;

import java.util.Map;
import static dev.taptsecd.chemicalwonders.chemistry.Substances.*;

public final class Reactions {
    /** 2 NaCl + 2 H2O -> 2 NaOH + H2 + Cl2. Water is the brine's solvent. */
    public static final Reaction BRINE_ELECTROLYSIS = new Reaction(
            Map.of(SODIUM_CHLORIDE, 2, WATER, 2),
            Map.of(SODIUM_HYDROXIDE, 2, HYDROGEN_GAS, 1, CHLORINE_GAS, 1));

    private Reactions() {}
}
