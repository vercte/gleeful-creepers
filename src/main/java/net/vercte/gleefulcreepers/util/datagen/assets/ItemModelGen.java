package net.vercte.gleefulcreepers.util.datagen.assets;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.vercte.gleefulcreepers.GleefulCreepers;

public class ItemModelGen extends ItemModelProvider {
    public ItemModelGen(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, GleefulCreepers.ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ResourceLocation egg = ForgeRegistries.ITEMS.getKey(GleefulCreepers.GLEEPER_SPAWN_EGG.get());
        getBuilder(egg.toString())
            .parent(new ModelFile.UncheckedModelFile("item/template_spawn_egg"));
    }
}
