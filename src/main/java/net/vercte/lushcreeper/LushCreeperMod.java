package net.vercte.lushcreeper;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.vercte.lushcreeper.creeper.LushCreeper;
import net.vercte.lushcreeper.util.datagen.LushCreeperModDatagen;

import java.util.function.Supplier;

@Mod(LushCreeperMod.ID)
public class LushCreeperMod {
    public static final String ID = "lush_creeper";

    public LushCreeperMod(IEventBus bus) {
        ENTITY_TYPES.register(bus);
        ITEMS.register(bus);

        bus.addListener(this::registerEntityAttributes);
        bus.addListener(this::buildCreativeTabs);

        bus.addListener(LushCreeperModDatagen::gatherData);
    }

    private void registerEntityAttributes(EntityAttributeCreationEvent entityAttributeCreationEvent) {
        entityAttributeCreationEvent.put(LUSH_CREEPER.get(), LushCreeper.createAttributes().build());
    }

    private void buildCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() != CreativeModeTabs.SPAWN_EGGS) return;
        event.insertAfter(Items.CREEPER_SPAWN_EGG.getDefaultInstance(), LUSH_CREEPER_SPAWN_EGG.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }

    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, ID);

    public static final Supplier<EntityType<LushCreeper>> LUSH_CREEPER = ENTITY_TYPES.register(
            "lush_creeper",
            () -> EntityType.Builder.of(LushCreeper::new, MobCategory.MONSTER).sized(0.6f, 1.4f).clientTrackingRange(8)
                    .build("lush_creeper:lush_creeper")
    );

    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ID);

    public static final DeferredItem<DeferredSpawnEggItem> LUSH_CREEPER_SPAWN_EGG = ITEMS.register(
            "lush_creeper_spawn_egg",
            () -> new DeferredSpawnEggItem(LUSH_CREEPER, 0x70922d, 0xfd87cf, new Item.Properties())
    );

    public static ResourceLocation at(String path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path);
    }
}