package net.vercte.gleefulcreepers.util.datagen.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biomes;
import net.vercte.gleefulcreepers.GleefulCreepers;
import net.vercte.gleefulcreepers.GleefulTags;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class BiomeTagGen extends BiomeTagsProvider {
    public BiomeTagGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, GleefulCreepers.ID);
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider provider) {
        tag(GleefulTags.GLEEPER_SPAWNS_IN).add(Biomes.LUSH_CAVES);
    }
}
