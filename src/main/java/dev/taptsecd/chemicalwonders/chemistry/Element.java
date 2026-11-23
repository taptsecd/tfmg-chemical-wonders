package dev.taptsecd.chemicalwonders.chemistry;

/** All 118 named elements in atomic-number order. Identity does not imply obtainability. */
public enum Element {
    HYDROGEN(1, "H", "Hydrogen", 1),
    HELIUM(2, "He", "Helium", 1),
    LITHIUM(3, "Li", "Lithium", 2),
    BERYLLIUM(4, "Be", "Beryllium", 2),
    BORON(5, "B", "Boron", 2),
    CARBON(6, "C", "Carbon", 2),
    NITROGEN(7, "N", "Nitrogen", 2),
    OXYGEN(8, "O", "Oxygen", 2),
    FLUORINE(9, "F", "Fluorine", 2),
    NEON(10, "Ne", "Neon", 2),
    SODIUM(11, "Na", "Sodium", 3),
    MAGNESIUM(12, "Mg", "Magnesium", 3),
    ALUMINIUM(13, "Al", "Aluminium", 3),
    SILICON(14, "Si", "Silicon", 3),
    PHOSPHORUS(15, "P", "Phosphorus", 3),
    SULFUR(16, "S", "Sulfur", 3),
    CHLORINE(17, "Cl", "Chlorine", 3),
    ARGON(18, "Ar", "Argon", 3),
    POTASSIUM(19, "K", "Potassium", 4),
    CALCIUM(20, "Ca", "Calcium", 4),
    SCANDIUM(21, "Sc", "Scandium", 4),
    TITANIUM(22, "Ti", "Titanium", 4),
    VANADIUM(23, "V", "Vanadium", 4),
    CHROMIUM(24, "Cr", "Chromium", 4),
    MANGANESE(25, "Mn", "Manganese", 4),
    IRON(26, "Fe", "Iron", 4),
    COBALT(27, "Co", "Cobalt", 4),
    NICKEL(28, "Ni", "Nickel", 4),
    COPPER(29, "Cu", "Copper", 4),
    ZINC(30, "Zn", "Zinc", 4),
    GALLIUM(31, "Ga", "Gallium", 4),
    GERMANIUM(32, "Ge", "Germanium", 4),
    ARSENIC(33, "As", "Arsenic", 4),
    SELENIUM(34, "Se", "Selenium", 4),
    BROMINE(35, "Br", "Bromine", 4),
    KRYPTON(36, "Kr", "Krypton", 4),
    RUBIDIUM(37, "Rb", "Rubidium", 5),
    STRONTIUM(38, "Sr", "Strontium", 5),
    YTTRIUM(39, "Y", "Yttrium", 5),
    ZIRCONIUM(40, "Zr", "Zirconium", 5),
    NIOBIUM(41, "Nb", "Niobium", 5),
    MOLYBDENUM(42, "Mo", "Molybdenum", 5),
    TECHNETIUM(43, "Tc", "Technetium", 5),
    RUTHENIUM(44, "Ru", "Ruthenium", 5),
    RHODIUM(45, "Rh", "Rhodium", 5),
    PALLADIUM(46, "Pd", "Palladium", 5),
    SILVER(47, "Ag", "Silver", 5),
    CADMIUM(48, "Cd", "Cadmium", 5),
    INDIUM(49, "In", "Indium", 5),
    TIN(50, "Sn", "Tin", 5),
    ANTIMONY(51, "Sb", "Antimony", 5),
    TELLURIUM(52, "Te", "Tellurium", 5),
    IODINE(53, "I", "Iodine", 5),
    XENON(54, "Xe", "Xenon", 5),
    CAESIUM(55, "Cs", "Caesium", 6),
    BARIUM(56, "Ba", "Barium", 6),
    LANTHANUM(57, "La", "Lanthanum", 6),
    CERIUM(58, "Ce", "Cerium", 6),
    PRASEODYMIUM(59, "Pr", "Praseodymium", 6),
    NEODYMIUM(60, "Nd", "Neodymium", 6),
    PROMETHIUM(61, "Pm", "Promethium", 6),
    SAMARIUM(62, "Sm", "Samarium", 6),
    EUROPIUM(63, "Eu", "Europium", 6),
    GADOLINIUM(64, "Gd", "Gadolinium", 6),
    TERBIUM(65, "Tb", "Terbium", 6),
    DYSPROSIUM(66, "Dy", "Dysprosium", 6),
    HOLMIUM(67, "Ho", "Holmium", 6),
    ERBIUM(68, "Er", "Erbium", 6),
    THULIUM(69, "Tm", "Thulium", 6),
    YTTERBIUM(70, "Yb", "Ytterbium", 6),
    LUTETIUM(71, "Lu", "Lutetium", 6),
    HAFNIUM(72, "Hf", "Hafnium", 6),
    TANTALUM(73, "Ta", "Tantalum", 6),
    TUNGSTEN(74, "W", "Tungsten", 6),
    RHENIUM(75, "Re", "Rhenium", 6),
    OSMIUM(76, "Os", "Osmium", 6),
    IRIDIUM(77, "Ir", "Iridium", 6),
    PLATINUM(78, "Pt", "Platinum", 6),
    GOLD(79, "Au", "Gold", 6),
    MERCURY(80, "Hg", "Mercury", 6),
    THALLIUM(81, "Tl", "Thallium", 6),
    LEAD(82, "Pb", "Lead", 6),
    BISMUTH(83, "Bi", "Bismuth", 6),
    POLONIUM(84, "Po", "Polonium", 6),
    ASTATINE(85, "At", "Astatine", 6),
    RADON(86, "Rn", "Radon", 6),
    FRANCIUM(87, "Fr", "Francium", 7),
    RADIUM(88, "Ra", "Radium", 7),
    ACTINIUM(89, "Ac", "Actinium", 7),
    THORIUM(90, "Th", "Thorium", 7),
    PROTACTINIUM(91, "Pa", "Protactinium", 7),
    URANIUM(92, "U", "Uranium", 7),
    NEPTUNIUM(93, "Np", "Neptunium", 7),
    PLUTONIUM(94, "Pu", "Plutonium", 7),
    AMERICIUM(95, "Am", "Americium", 7),
    CURIUM(96, "Cm", "Curium", 7),
    BERKELIUM(97, "Bk", "Berkelium", 7),
    CALIFORNIUM(98, "Cf", "Californium", 7),
    EINSTEINIUM(99, "Es", "Einsteinium", 7),
    FERMIUM(100, "Fm", "Fermium", 7),
    MENDELEVIUM(101, "Md", "Mendelevium", 7),
    NOBELIUM(102, "No", "Nobelium", 7),
    LAWRENCIUM(103, "Lr", "Lawrencium", 7),
    RUTHERFORDIUM(104, "Rf", "Rutherfordium", 7),
    DUBNIUM(105, "Db", "Dubnium", 7),
    SEABORGIUM(106, "Sg", "Seaborgium", 7),
    BOHRIUM(107, "Bh", "Bohrium", 7),
    HASSIUM(108, "Hs", "Hassium", 7),
    MEITNERIUM(109, "Mt", "Meitnerium", 7),
    DARMSTADTIUM(110, "Ds", "Darmstadtium", 7),
    ROENTGENIUM(111, "Rg", "Roentgenium", 7),
    COPERNICIUM(112, "Cn", "Copernicium", 7),
    NIHONIUM(113, "Nh", "Nihonium", 7),
    FLEROVIUM(114, "Fl", "Flerovium", 7),
    MOSCOVIUM(115, "Mc", "Moscovium", 7),
    LIVERMORIUM(116, "Lv", "Livermorium", 7),
    TENNESSINE(117, "Ts", "Tennessine", 7),
    OGANESSON(118, "Og", "Oganesson", 7);

    private final int atomicNumber;
    private final String symbol;
    private final String displayName;
    private final int period;

    Element(int atomicNumber, String symbol, String displayName, int period) {
        this.atomicNumber = atomicNumber;
        this.symbol = symbol;
        this.displayName = displayName;
        this.period = period;
    }

    public int atomicNumber() { return atomicNumber; }
    public String symbol() { return symbol; }
    public String displayName() { return displayName; }
    public int period() { return period; }
    public String id() { return name().toLowerCase(java.util.Locale.ROOT); }

    public ElementFamily family() {
        if (atomicNumber >= 57 && atomicNumber <= 71) return ElementFamily.LANTHANIDE;
        if (atomicNumber >= 89 && atomicNumber <= 103) return ElementFamily.ACTINIDE;
        return switch (this) {
            case LITHIUM, SODIUM, POTASSIUM, RUBIDIUM, CAESIUM, FRANCIUM -> ElementFamily.ALKALI_METAL;
            case BERYLLIUM, MAGNESIUM, CALCIUM, STRONTIUM, BARIUM, RADIUM -> ElementFamily.ALKALINE_EARTH_METAL;
            case HELIUM, NEON, ARGON, KRYPTON, XENON, RADON, OGANESSON -> ElementFamily.NOBLE_GAS;
            case FLUORINE, CHLORINE, BROMINE, IODINE, ASTATINE, TENNESSINE -> ElementFamily.HALOGEN;
            case BORON, SILICON, GERMANIUM, ARSENIC, ANTIMONY, TELLURIUM -> ElementFamily.METALLOID;
            case HYDROGEN, CARBON, NITROGEN, OXYGEN, PHOSPHORUS, SULFUR, SELENIUM -> ElementFamily.OTHER_NONMETAL;
            case ALUMINIUM, GALLIUM, INDIUM, TIN, THALLIUM, LEAD, BISMUTH, POLONIUM,
                 NIHONIUM, FLEROVIUM, MOSCOVIUM, LIVERMORIUM -> ElementFamily.POST_TRANSITION_METAL;
            default -> ElementFamily.TRANSITION_METAL;
        };
    }
}
