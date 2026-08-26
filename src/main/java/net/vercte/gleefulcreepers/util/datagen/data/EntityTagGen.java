package net.vercte.gleefulcreepers.util.datagen.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.vercte.gleefulcreepers.GleefulCreepers;
import net.vercte.gleefulcreepers.GleefulTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class EntityTagGen extends IntrinsicHolderTagsProvider<EntityType<?>> {
    public EntityTagGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, @Nullable ExistingFileHelper existingFileHelper) {
        super(
                output,
                BuiltInRegistries.ENTITY_TYPE.key(),
                registries,
                (e) -> BuiltInRegistries.ENTITY_TYPE.getResourceKey(e).orElseThrow(),
                GleefulCreepers.ID,
                existingFileHelper
        );
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider provider) {
        tag(GleefulTags.GLEEPER_FORGIVES)
                .add(EntityType.AXOLOTL)
                .add(EntityType.BEE)
                .add(GleefulCreepers.GLEEPER.get());
    }
}
