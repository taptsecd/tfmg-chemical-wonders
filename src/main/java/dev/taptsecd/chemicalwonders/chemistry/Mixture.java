package dev.taptsecd.chemicalwonders.chemistry;

import java.util.Map;
import java.util.Objects;

/** Positive integer molar parts of substances; components remain chemically distinct. */
public record Mixture(Map<Substance, Integer> parts) {
    public Mixture {
        parts = Map.copyOf(Objects.requireNonNull(parts));
        if (parts.size() < 2 || parts.values().stream().anyMatch(count -> count <= 0)) {
            throw new IllegalArgumentException("A mixture needs at least two substances with positive parts");
        }
    }

    /** Element inventory for this batch, for checking conservation in future recipes. */
    public Composition elementalInventory() {
        return parts.entrySet().stream()
                .map(entry -> entry.getKey().composition().times(entry.getValue()))
                .reduce(Composition::plus).orElseThrow();
    }
}
