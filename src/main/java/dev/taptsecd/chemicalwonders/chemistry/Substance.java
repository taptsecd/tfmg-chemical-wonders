package dev.taptsecd.chemicalwonders.chemistry;

import java.util.Objects;

/** Chemical identity is separate from physical form (gas, solution, powder, ingot). */
public record Substance(String id, Composition composition) {
    public Substance {
        Objects.requireNonNull(id);
        Objects.requireNonNull(composition);
        if (!id.matches("[a-z][a-z0-9_]*")) throw new IllegalArgumentException("Invalid substance ID: " + id);
    }

    public boolean isCompound() { return composition.atoms().size() > 1; }

    public static Substance element(Element element) {
        return new Substance(element.id(), Composition.element(element));
    }
}
