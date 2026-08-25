package net.vercte.lushcreeper.util.datagen.assets;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.vercte.lushcreeper.LushCreeperMod;

public class ItemModelGen extends ItemModelProvider {
    public ItemModelGen(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, LushCreeperMod.ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        spawnEggItem(LushCreeperMod.LUSH_CREEPER_SPAWN_EGG.get());
    }
}
