package net.vercte.gleefulcreepers.util.datagen.assets;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.vercte.gleefulcreepers.GleefulCreepers;

public class LangGen extends LanguageProvider {
    public LangGen(PackOutput output) {
        super(output, GleefulCreepers.ID, "en_us");
    }

    static final String SOUND_GLEEPER_ANGER= "sound.gleeful_creepers.gleeper_anger";
    static final String SOUND_GLEEPER_HURT = "sound.gleeful_creepers.gleeper_hurt";
    static final String SOUND_GLEEPER_DEATH = "sound.gleeful_creepers.gleeper_death";

    @Override
    protected void addTranslations() {
        addEntityType(GleefulCreepers.GLEEPER, "Gleeper");
        addItem(GleefulCreepers.GLEEPER_SPAWN_EGG, "Gleeper Spawn Egg");
        add(SOUND_GLEEPER_ANGER, "Gleeper angers");
        add(SOUND_GLEEPER_HURT, "Gleeper hurts");
        add(SOUND_GLEEPER_DEATH, "Gleeper dies");
    }
}
