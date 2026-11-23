# TFMG: Chemical Wonders

Minecraft **1.21.1**, NeoForge **21.1.264**, Java **21**.

Mod ID: `tfmg_chemical_wonders`. Initial version: `0.1.0`.

## Development

With Nix, enter the pinned development environment (Java 21, Gradle 8.14.4,
and Git):

```sh
nix develop path:.
gradle build
```

`path:.` includes the flake before it has been added to Git. The included
`flake.lock` pins nixpkgs; track it along with `flake.nix`. After tracking the
files, plain `nix develop` also works. If flakes are not enabled in your Nix configuration, use
`nix --extra-experimental-features 'nix-command flakes' develop path:.`.

Inside the shell, use `gradle runClient`, `gradle runServer`, or
`gradle chemistryCheck`. Using `gradle` directly uses the Nix-provided Gradle
rather than downloading a separate wrapper distribution. Gradle still needs
network access for Minecraft, NeoForge, and plugin dependencies on first use.

Without Nix, install a 64-bit JDK 21 and set `JAVA_HOME` or add Java to `PATH`.
Import this directory as a Gradle project in your IDE. The included wrapper
uses Gradle 8.14.4 and downloads Gradle and dependencies on first use.

```sh
./gradlew build
./gradlew runClient
./gradlew runServer
./gradlew runData
```

On Windows, use `gradlew.bat`. Built jars are written to `build/libs/`.
For a development server, review and accept Minecraft's EULA in its generated
run directory before launching again.

Version and mod identity settings live in `gradle.properties`.
The development runtime pins **TFMG Community Edition 1.3.3a** and
**Create 6.0.10**, including their bundled dependencies. The mod requires both.
The server integration checks run on NeoForge 21.1.264.

## Chemistry foundation

`chemistry.PeriodicTable` provides all 118 elements by atomic number, symbol,
and stable lowercase ID, with period and family classifications. Families are
gameplay groupings; they do not imply measured behavior for superheavy elements.

`Composition` stores exact positive integer atom counts per formula unit.
`Substance` associates a stable identity with a composition; `Mixture` stores
positive integer molar parts of distinct substances. Physical form and processing
yield are separate concerns. The initial compound definitions are water, carbon
dioxide, iron(III) oxide, silicon dioxide, and sodium chloride. Sodium chloride
has an obtainable item linked to its chemical identity.

Extraction and synthesis recipes will explicitly map existing materials to
substances and specify amounts, conditions, and yields. Knowing a composition
does not automatically make a material decomposable or craftable.

Run `./gradlew chemistryCheck` to check identities, invalid inputs, immutable
compositions, and atom conservation. These checks also run with `./gradlew build`.
No in-game periodic-table interface exists yet.

References: [PubChem periodic table](https://pubchem.ncbi.nlm.nih.gov/periodic-table/)
and [TFMG Community Edition](https://github.com/The-Metallurgists/Create-TFMG-CE).

## Sodium chloride

Two sources feed the same NaCl material:

- **Rock salt:** solid evaporite lenses, radius 4–8 blocks and up to five blocks
  thick, just below the surface in beaches, deserts, and badlands. They replace
  sediment and stone as bulk deposits rather than ore speckles. They appear in
  newly generated chunks; mine with a stone pickaxe or better.
- **Seawater:** use an empty bucket on an open water source in an ocean or beach
  biome, including Oh The Biomes We've Gone shores and oceans. Rivers and inland
  water stay fresh. This is a biome-based gameplay
  approximation: player-placed water in coastal biomes also counts as seawater.
  Automated ocean intake is not implemented yet.

| Process | Input | Output | Equipment |
| --- | --- | --- | --- |
| Manual crushing | 1 rock salt | 2 sodium chloride | Crafting grid |
| Powered crushing | 1 rock salt | 4 sodium chloride | Create crushing wheels |
| Concentration | 1,000 mB seawater | 250 mB brine | Vat + industrial mixer, heat level 1 |
| Distillation | 1,000 mB seawater | 250 mB brine + 750 mB water | TFMG distillation tower, two fluid outputs |
| Crystallization | 250 mB brine | 1 sodium chloride | Vat + industrial mixer, heat level 1 |
| Dissolution | 1 sodium chloride + 250 mB water | 250 mB brine | Vat + industrial mixer |

The vat concentration route vents the evaporated solvent. Amounts are initial
balance values, not real-world mass or molar units. Seawater and brine are
pumpable fluids with buckets and cannot create infinite source blocks. The
initial art reuses vanilla sugar, calcite, and water textures.

Run `gradle runGameTestServer` inside the Nix shell for integration checks:
recipes load with Create and TFMG CE, solutions have valid buckets, source-water
classification excludes rivers/flowing/waterlogged water, and deposits form
solid lenses. The checks do not yet exercise a complete running factory.
Sodium metal and weapon infusions are not implemented. Brine electrolysis is
described below.

## Chemical processing

The first reactive process is chlor-alkali electrolysis:

`2 NaCl + 2 H2O -> 2 NaOH + H2 + Cl2`

The brine already includes the solvent water. A **steel or fireproof chemical
vat with two electrodes** consumes **500 mB brine** per operation and produces:

- 500 mB sodium hydroxide solution
- 250 mB chlorine
- 250 mB of TFMG's existing hydrogen

These mB amounts are game balance values. Electrode power is handled by TFMG;
this mod does not add a parallel electricity system. The recipe takes 200
processing-time units and needs all three outputs handled. It has no sodium-metal
output and does not use the three-electrode arc-furnace operation.

Chlorine is a tank-only process gas with no bucket or placeable block. Sodium
hydroxide solution has normal fluid handling and a bucket. Both currently have
no contact damage or weapon effects.

| Process | Input | Output | Equipment |
| --- | --- | --- | --- |
| Hydroxide crystallization | 250 mB hydroxide solution | 1 sodium hydroxide | Vat + industrial mixer, heat level 1 |
| Hydroxide dissolution | 1 sodium hydroxide + 250 mB water | 250 mB hydroxide solution | Vat + industrial mixer |

`chemistry.Reaction` checks positive formula-unit coefficients and exact atom
conservation. `Reactions.BRINE_ELECTROLYSIS` defines the chemical equation;
machine recipes separately define the gameplay amounts and conditions.
The current checks verify decoded TFMG recipe requirements and outputs, as well
as the chemistry model. They do not simulate an entire powered factory.

Chemistry reference: [Euro Chlor's chlor-alkali process description](https://eurochlor.org/app/uploads/2021/09/WCC-CA-Manufacturing-Processes-brochure.pdf).

## Oh The Biomes We've Gone compatibility

BWG is optional. Seawater collection recognizes its Basalt Barrera, Dacite Shore,
Rainbow Beach, Dead Sea, and Lush Stacks through biome tags. Its inland marshes
stay fresh. Salt deposits can generate in the three shore biomes, including
colored sand, basalt, and white-dacite substrates. Existing chunks are unchanged.

Datapacks can extend `tfmg_chemical_wonders:saline_water` (collection),
`tfmg_chemical_wonders:salt_coasts` (coastal deposits), and the block tag
`tfmg_chemical_wonders:rock_salt_replaceable` (deposit substrates). Optional BWG
references do not require installing the mod. The shared `c:is_beach` and
`c:is_ocean` biome tags are recognized too.

To run the integration checks with pinned BWG 2.6.3 and its dependencies:

```sh
gradle -PbwgCompat=true runGameTestServer
```

Run `gradle runGameTestServer` without the property to verify the normal runtime
without BWG. The BWG-specific test checks its five coastal/ocean biomes, freshwater
exclusion, and the actual deposit predicate against its shore blocks.

## Repository

Project display name: **TFMG: Chemical Wonders**.
Filesystem/remote slug: `TFMG-chemical-wonders`.

## License

All rights reserved until a project license is selected.
