package net.vercte.gleefulcreepers.util.datagen.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.vercte.gleefulcreepers.GleefulCreepers;
import net.vercte.gleefulcreepers.GleefulTags;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class EntityTagGen extends EntityTypeTagsProvider {
    public EntityTagGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, GleefulCreepers.ID);
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider provider) {
        tag(GleefulTags.GLEEPER_FORGIVES)
                .add(EntityType.AXOLOTL)
                .add(EntityType.BEE)
                .add(GleefulCreepers.GLEEPER.get());
    }
}
