package net.vercte.gleefulcreepers.util.datagen.data;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.entity.EntityType;
import net.vercte.gleefulcreepers.GleefulCreepers;
import net.vercte.gleefulcreepers.GleefulTags;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class EntityTagGen extends FabricTagsProvider.EntityTypeTagsProvider {
    public EntityTagGen(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider provider) {
        valueLookupBuilder(GleefulTags.GLEEPER_FORGIVES)
                .add(EntityType.AXOLOTL)
                .add(EntityType.BEE)
                .add(GleefulCreepers.GLEEPER);
    }
}
