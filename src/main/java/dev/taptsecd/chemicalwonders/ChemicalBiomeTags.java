package dev.taptsecd.chemicalwonders;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

/** Datapacks can extend salinity and coastal generation without Java integrations. */
public final class ChemicalBiomeTags {
    public static final TagKey<Biome> SALINE_WATER = tag("saline_water");
    public static final TagKey<Biome> SALT_COASTS = tag("salt_coasts");
    public static final TagKey<Biome> HAS_ROCK_SALT = tag("has_rock_salt");

    private static TagKey<Biome> tag(String path) {
        return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(ChemicalWonders.MOD_ID, path));
    }

    private ChemicalBiomeTags() {}
}
