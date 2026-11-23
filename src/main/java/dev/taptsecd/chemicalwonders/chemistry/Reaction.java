package dev.taptsecd.chemicalwonders.chemistry;

import java.util.Map;
import java.util.Objects;

/** Balanced formula-unit accounting. Machine conditions and game quantities belong to recipes. */
public record Reaction(Map<Substance, Integer> reactants, Map<Substance, Integer> products) {
    public Reaction {
        reactants = validateSide(reactants);
        products = validateSide(products);
        if (!inventory(reactants).equals(inventory(products))) {
            throw new IllegalArgumentException("Reaction does not conserve atoms");
        }
    }

    private static Map<Substance, Integer> validateSide(Map<Substance, Integer> side) {
        var result = Map.copyOf(Objects.requireNonNull(side));
        if (result.isEmpty() || result.values().stream().anyMatch(count -> count <= 0)) {
            throw new IllegalArgumentException("A reaction side needs positive formula-unit coefficients");
        }
        return result;
    }

    private static Composition inventory(Map<Substance, Integer> side) {
        return side.entrySet().stream().map(entry -> entry.getKey().composition().times(entry.getValue()))
                .reduce(Composition::plus).orElseThrow();
    }
}
