package net.vercte.gleefulcreepers.util.datagen.assets;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.vercte.gleefulcreepers.GleefulCreepers;
import org.jetbrains.annotations.NotNull;

public class ModelGen extends FabricModelProvider {
    public ModelGen(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(@NotNull BlockModelGenerators blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        itemModelGenerator.generateFlatItem(GleefulCreepers.GLEEPER_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
    }
}
