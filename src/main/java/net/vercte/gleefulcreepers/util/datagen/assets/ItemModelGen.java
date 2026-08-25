package net.vercte.gleefulcreepers.util.datagen.assets;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.vercte.gleefulcreepers.GleefulCreepers;

public class ItemModelGen extends ItemModelProvider {
    public ItemModelGen(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, GleefulCreepers.ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        spawnEggItem(GleefulCreepers.GLEEPER_SPAWN_EGG.get());
    }
}
