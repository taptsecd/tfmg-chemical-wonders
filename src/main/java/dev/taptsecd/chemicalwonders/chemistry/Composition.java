package dev.taptsecd.chemicalwonders.chemistry;

import java.util.Map;
import java.util.Objects;

/** Exact atoms per formula unit, not mass percentages or recoverable item counts. */
public record Composition(Map<Element, Integer> atoms) {
    public Composition {
        atoms = Map.copyOf(Objects.requireNonNull(atoms));
        if (atoms.isEmpty() || atoms.values().stream().anyMatch(count -> count <= 0)) {
            throw new IllegalArgumentException("A composition needs positive atom counts");
        }
    }

    public static Composition element(Element element) { return new Composition(Map.of(element, 1)); }

    public Composition plus(Composition other) {
        var result = new java.util.EnumMap<Element, Integer>(Element.class);
        result.putAll(atoms);
        other.atoms.forEach((element, count) -> result.merge(element, count, Math::addExact));
        return new Composition(result);
    }

    public Composition times(int units) {
        if (units <= 0) throw new IllegalArgumentException("Formula units must be positive");
        var result = new java.util.EnumMap<Element, Integer>(Element.class);
        atoms.forEach((element, count) -> result.put(element, Math.multiplyExact(count, units)));
        return new Composition(result);
    }
}
