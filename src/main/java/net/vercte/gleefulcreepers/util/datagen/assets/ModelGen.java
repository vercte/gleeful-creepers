package net.vercte.gleefulcreepers.util.datagen.assets;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import net.vercte.gleefulcreepers.GleefulCreepers;
import org.jetbrains.annotations.NotNull;

public class ModelGen extends ModelProvider {
    public ModelGen(PackOutput output) {
        super(output, GleefulCreepers.ID);
    }

    @Override
    protected void registerModels(@NotNull BlockModelGenerators blockModels, @NotNull ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(GleefulCreepers.GLEEPER_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
    }
}
