package net.vercte.lushcreeper;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.vercte.lushcreeper.creeper.LushCreeper;

import java.util.function.Supplier;

@Mod(LushCreeperMod.ID)
public class LushCreeperMod {
    public static final String ID = "lush_creeper";

    public LushCreeperMod(IEventBus bus) {
        ENTITY_TYPES.register(bus);

        bus.addListener(this::registerEntityAttributes);
    }

    private void registerEntityAttributes(EntityAttributeCreationEvent entityAttributeCreationEvent) {
        entityAttributeCreationEvent.put(LUSH_CREEPER.get(), LushCreeper.createAttributes().build());
    }

    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, ID);

    public static final Supplier<EntityType<LushCreeper>> LUSH_CREEPER = ENTITY_TYPES.register(
            "lush_creeper",
            () -> EntityType.Builder.of(LushCreeper::new, MobCategory.MONSTER).sized(0.6f, 1.4f).clientTrackingRange(8)
                    .build("lush_creeper:lush_creeper")
    );

    public static ResourceLocation at(String path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path);
    }
}