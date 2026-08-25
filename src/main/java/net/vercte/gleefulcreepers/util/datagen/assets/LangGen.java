package net.vercte.gleefulcreepers.util.datagen.assets;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.vercte.gleefulcreepers.GleefulCreepers;

public class LangGen extends LanguageProvider {
    public LangGen(PackOutput output) {
        super(output, GleefulCreepers.ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        addEntityType(GleefulCreepers.GLEEPER, "Gleeper");
        addItem(GleefulCreepers.GLEEPER_SPAWN_EGG, "Gleeper Spawn Egg");
    }
}
