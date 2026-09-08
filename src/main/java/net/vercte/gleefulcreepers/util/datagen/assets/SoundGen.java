package net.vercte.gleefulcreepers.util.datagen.assets;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import net.vercte.gleefulcreepers.GleefulCreepers;
import net.vercte.gleefulcreepers.GleefulSounds;

public class SoundGen extends SoundDefinitionsProvider {
    public SoundGen(PackOutput output) {
        super(output, GleefulCreepers.ID);
    }

    @Override
    public void registerSounds() {
        add(
                GleefulSounds.GLEEPER_ANGER.get(),
                SoundDefinition.definition().with(
                                sound(GleefulCreepers.at("mob/gleeper/anger"), SoundDefinition.SoundType.SOUND)
                        )
                        .subtitle(LangGen.SOUND_GLEEPER_HURT)
        );

        add(
                GleefulSounds.GLEEPER_HURT.get(),
                SoundDefinition.definition().with(
                        sound("minecraft:entity.creeper.hurt", SoundDefinition.SoundType.EVENT)
                                .pitch(1.5f)
                )
                .subtitle(LangGen.SOUND_GLEEPER_HURT)
        );

        add(
                GleefulSounds.GLEEPER_DEATH.get(),
                SoundDefinition.definition().with(
                                sound("minecraft:entity.creeper.death", SoundDefinition.SoundType.EVENT)
                                        .pitch(1.3f)
                        )
                        .subtitle(LangGen.SOUND_GLEEPER_DEATH)
        );
    }
}
