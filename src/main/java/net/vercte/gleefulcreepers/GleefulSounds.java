package net.vercte.gleefulcreepers;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

public class GleefulSounds {
    public static final SoundEvent GLEEPER_ANGER = dynamicRange("entity.gleeper.anger");
    public static final SoundEvent GLEEPER_HURT = dynamicRange("entity.gleeper.hurt");
    public static final SoundEvent GLEEPER_DEATH = dynamicRange("entity.gleeper.death");

    private static SoundEvent dynamicRange(String path) {
        return Registry.register(
                BuiltInRegistries.SOUND_EVENT,
                GleefulCreepers.at(path),
                SoundEvent.createVariableRangeEvent(GleefulCreepers.at(path))
        );
    }

    public static void loadAndRegister() {}
}
