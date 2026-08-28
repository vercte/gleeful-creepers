package net.vercte.gleefulcreepers.util.datagen.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.vercte.gleefulcreepers.GleefulTags;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ItemTagGen extends ItemTagsProvider {
    public ItemTagGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CompletableFuture.completedFuture(null));
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider provider) {
        tag(GleefulTags.ACTS_AS_BONE_MEAL)
                .add(Items.BONE_MEAL);
    }
}
