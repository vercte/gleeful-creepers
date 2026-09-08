package net.vercte.gleefulcreepers.util.datagen.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.entity.EntityType;
import net.vercte.gleefulcreepers.GleefulCreepers;
import net.vercte.gleefulcreepers.GleefulTags;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class EntityTagGen extends FabricTagProvider.EntityTypeTagProvider {
    public EntityTagGen(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider provider) {
        getOrCreateTagBuilder(GleefulTags.GLEEPER_FORGIVES)
                .add(EntityType.AXOLOTL)
                .add(EntityType.BEE)
                .add(GleefulCreepers.GLEEPER);
    }
}
