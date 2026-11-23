package dev.taptsecd.chemicalwonders.chemistry;

import java.util.Map;
import static dev.taptsecd.chemicalwonders.chemistry.Element.*;

/** Initial formula definitions. These do not automatically create extraction or synthesis recipes. */
public final class Substances {
    public static final Substance SODIUM_CHLORIDE = new Substance("sodium_chloride", new Composition(Map.of(SODIUM, 1, CHLORINE, 1)));
    public static final Substance SODIUM_HYDROXIDE = new Substance("sodium_hydroxide", new Composition(Map.of(SODIUM, 1, OXYGEN, 1, HYDROGEN, 1)));
    public static final Substance HYDROGEN_GAS = new Substance("hydrogen", new Composition(Map.of(HYDROGEN, 2)));
    public static final Substance CHLORINE_GAS = new Substance("chlorine", new Composition(Map.of(CHLORINE, 2)));
    public static final Substance WATER = new Substance("water", new Composition(Map.of(HYDROGEN, 2, OXYGEN, 1)));
    public static final Substance CARBON_DIOXIDE = new Substance("carbon_dioxide", new Composition(Map.of(CARBON, 1, OXYGEN, 2)));
    public static final Substance IRON_OXIDE = new Substance("iron_iii_oxide", new Composition(Map.of(IRON, 2, OXYGEN, 3)));
    public static final Substance SILICON_DIOXIDE = new Substance("silicon_dioxide", new Composition(Map.of(SILICON, 1, OXYGEN, 2)));

    private Substances() {}
}
