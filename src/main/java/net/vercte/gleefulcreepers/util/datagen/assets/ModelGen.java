package net.vercte.gleefulcreepers.util.datagen.assets;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceLocation;
import net.vercte.gleefulcreepers.GleefulCreepers;

import java.util.Optional;

public class ModelGen extends FabricModelProvider {
    public ModelGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        itemModelGenerator.generateFlatItem(
                GleefulCreepers.GLEEPER_SPAWN_EGG,
                new ModelTemplate(
                        Optional.of(ResourceLocation.withDefaultNamespace("item/template_spawn_egg")),
                        Optional.empty()
                )
        );
    }
}
