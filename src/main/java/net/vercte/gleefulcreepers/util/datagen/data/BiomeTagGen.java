package net.vercte.gleefulcreepers.util.datagen.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.vercte.gleefulcreepers.GleefulCreepers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class BiomeTagGen extends TagsProvider<Biome> {
    public BiomeTagGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, Registries.BIOME, registries, GleefulCreepers.ID, existingFileHelper);
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider provider) {
        tag(GleefulCreepers.GLEEPER_SPAWNS_IN).add(Biomes.LUSH_CAVES);
    }
}
