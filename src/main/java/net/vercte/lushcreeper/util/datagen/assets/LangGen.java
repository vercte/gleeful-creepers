package net.vercte.lushcreeper.util.datagen.assets;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.vercte.lushcreeper.LushCreeperMod;

public class LangGen extends LanguageProvider {
    public LangGen(PackOutput output) {
        super(output, LushCreeperMod.ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        addEntityType(LushCreeperMod.LUSH_CREEPER, "Lush Creeper");
        addItem(LushCreeperMod.LUSH_CREEPER_SPAWN_EGG, "Lush Creeper Spawn Egg");
    }
}
