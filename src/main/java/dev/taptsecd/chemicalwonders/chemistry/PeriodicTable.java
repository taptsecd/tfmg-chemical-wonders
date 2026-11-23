package dev.taptsecd.chemicalwonders.chemistry;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class PeriodicTable {
    private static final List<Element> ELEMENTS = List.of(Element.values());
    private static final Map<String, Element> BY_SYMBOL = ELEMENTS.stream()
            .collect(Collectors.toUnmodifiableMap(Element::symbol, Function.identity()));
    private static final Map<String, Element> BY_ID = ELEMENTS.stream()
            .collect(Collectors.toUnmodifiableMap(Element::id, Function.identity()));

    private PeriodicTable() {}

    public static List<Element> elements() { return ELEMENTS; }

    public static Element byAtomicNumber(int number) {
        if (number < 1 || number > ELEMENTS.size()) {
            throw new IllegalArgumentException("Atomic number out of range: " + number);
        }
        return ELEMENTS.get(number - 1);
    }

    /** Symbols are case-sensitive: Co is cobalt, not carbon and oxygen. */
    public static Optional<Element> bySymbol(String symbol) { return Optional.ofNullable(BY_SYMBOL.get(symbol)); }
    public static Optional<Element> byId(String id) { return Optional.ofNullable(BY_ID.get(id)); }
}
