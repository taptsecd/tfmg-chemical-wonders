package dev.taptsecd.chemicalwonders.chemistry;

import java.util.HashMap;
import java.util.Map;
import static dev.taptsecd.chemicalwonders.chemistry.Element.*;

/** Dependency-free model checks; also runnable directly with Java 21. */
public final class ChemistryChecks {
    public static void main(String[] args) {
        check(PeriodicTable.elements().size() == 118, "118 elements");
        for (int n = 1; n <= 118; n++) {
            var element = PeriodicTable.byAtomicNumber(n);
            check(element.atomicNumber() == n, "Atomic-number ordering");
            check(PeriodicTable.bySymbol(element.symbol()).orElseThrow() == element, "Unique symbols");
            check(PeriodicTable.byId(element.id()).orElseThrow() == element, "Unique IDs");
            check(element.period() >= 1 && element.period() <= 7, "Valid period");
            check(element.family() != null, "Family present");
        }
        check(PeriodicTable.byAtomicNumber(26) == IRON, "Iron identity");
        check(PeriodicTable.byAtomicNumber(118) == OGANESSON, "Last element");
        check(PeriodicTable.bySymbol("co").isEmpty(), "Case-sensitive symbols");
        check(SODIUM.family() == ElementFamily.ALKALI_METAL, "Sodium family");
        check(HYDROGEN.family() == ElementFamily.OTHER_NONMETAL, "Hydrogen is not an alkali metal");
        check(URANIUM.family() == ElementFamily.ACTINIDE, "Uranium family");
        rejects(() -> PeriodicTable.byAtomicNumber(0));
        rejects(() -> PeriodicTable.byAtomicNumber(119));
        rejects(() -> new Composition(Map.of()));
        rejects(() -> new Composition(Map.of(IRON, -1)));
        rejects(() -> Substances.WATER.composition().times(0));
        rejects(() -> new Substance("Bad ID", Composition.element(IRON)));
        rejects(() -> new Mixture(Map.of(Substances.WATER, 1)));
        rejects(() -> new Mixture(Map.of(Substances.WATER, 1, Substances.CARBON_DIOXIDE, 0)));
        var mutable = new HashMap<Element, Integer>();
        mutable.put(IRON, 2);
        var copied = new Composition(mutable);
        mutable.put(IRON, 5);
        check(copied.atoms().get(IRON) == 2, "Defensive copy");
        check(Substances.SODIUM_CHLORIDE.composition().equals(new Composition(Map.of(SODIUM, 1, CHLORINE, 1))), "NaCl formula");
        check(Substances.WATER.isCompound(), "Water is a compound");
        check(!new Substance("oxygen", new Composition(Map.of(OXYGEN, 2))).isCompound(),
                "Diatomic oxygen is elemental, not a compound");
        var mixture = new Mixture(Map.of(Substances.WATER, 2, Substances.CARBON_DIOXIDE, 1));
        check(mixture.elementalInventory().equals(new Composition(Map.of(HYDROGEN, 4, OXYGEN, 4, CARBON, 1))),
                "Mixture conserves constituent atoms");
        // 2 Fe2O3 + 3 C -> 4 Fe + 3 CO2: validate exact atom accounting.
        var input = Substances.IRON_OXIDE.composition().times(2).plus(Composition.element(CARBON).times(3));
        var output = Composition.element(IRON).times(4).plus(Substances.CARBON_DIOXIDE.composition().times(3));
        check(input.equals(output), "Balanced extraction inventory");
        var electrolysis = Reactions.BRINE_ELECTROLYSIS;
        check(electrolysis.products().get(Substances.SODIUM_HYDROXIDE) == 2, "Electrolysis yields hydroxide");
        check(electrolysis.products().get(Substances.HYDROGEN_GAS) == 1, "Hydrogen is diatomic");
        check(electrolysis.products().get(Substances.CHLORINE_GAS) == 1, "Chlorine is diatomic");
        rejects(() -> new Reaction(Map.of(Substances.SODIUM_CHLORIDE, 1), Map.of(Substances.SODIUM_HYDROXIDE, 1)));
        rejects(() -> new Reaction(Map.of(), Map.of(Substances.WATER, 1)));
        rejects(() -> new Reaction(Map.of(Substances.WATER, 0), Map.of(Substances.WATER, 1)));
        var reactants = new HashMap<>(electrolysis.reactants());
        var copiedReaction = new Reaction(reactants, electrolysis.products());
        reactants.clear();
        check(copiedReaction.reactants().size() == 2, "Reaction defensively copies coefficients");
        rejectsArithmetic(() -> new Composition(Map.of(IRON, Integer.MAX_VALUE)).times(2));
        System.out.println("Chemistry checks passed: 118 identities, validation, immutable compositions, and atom conservation.");
    }

    private static void check(boolean condition, String label) {
        if (!condition) throw new AssertionError(label);
    }

    private static void rejects(Runnable action) {
        try { action.run(); } catch (IllegalArgumentException expected) { return; }
        throw new AssertionError("Expected invalid input to be rejected");
    }

    private static void rejectsArithmetic(Runnable action) {
        try { action.run(); } catch (ArithmeticException expected) { return; }
        throw new AssertionError("Expected overflow to be rejected");
    }
}
