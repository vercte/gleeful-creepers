package net.vercte.gleefulcreepers;

import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class GleefulSounds {
    private static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, GleefulCreepers.ID);

    public static final Supplier<SoundEvent> GLEEPER_ANGER = dynamicRange("entity.gleeper.anger");
    public static final Supplier<SoundEvent> GLEEPER_HURT = dynamicRange("entity.gleeper.hurt");
    public static final Supplier<SoundEvent> GLEEPER_DEATH = dynamicRange("entity.gleeper.death");

    private static Supplier<SoundEvent> dynamicRange(String path) {
        return SOUND_EVENTS.register(path, () -> SoundEvent.createVariableRangeEvent(GleefulCreepers.at(path)));
    }

    public static void loadAndRegister(IEventBus bus) {
        SOUND_EVENTS.register(bus);
    }
}
