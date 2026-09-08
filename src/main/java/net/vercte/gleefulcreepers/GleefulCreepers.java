package net.vercte.gleefulcreepers;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.vercte.gleefulcreepers.gleeper.Gleeper;
import net.vercte.gleefulcreepers.util.datagen.GleefulDatagen;

import java.util.function.Supplier;

@Mod(GleefulCreepers.ID)
public class GleefulCreepers {
    public static final String ID = "gleeful_creepers";

    public GleefulCreepers(IEventBus bus, ModContainer container) {
        ENTITY_TYPES.register(bus);
        ITEMS.register(bus);

        GleefulSounds.loadAndRegister(bus);

        bus.addListener(this::registerEntityAttributes);
        bus.addListener(this::registerSpawnPlacements);
        bus.addListener(this::buildCreativeTabs);

        bus.addListener(GleefulDatagen::gatherData);

        container.registerConfig(ModConfig.Type.SERVER, GleefulConfig.SPEC);
    }

    private void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(GLEEPER.get(), Gleeper.createAttributes().build());
    }

    private void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(
                GLEEPER.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Gleeper::checkGleeperSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
    }

    private void buildCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() != CreativeModeTabs.SPAWN_EGGS) return;
        // I originally put it after the Creeper spawn egg,
        // but it turns out the Spawn Eggs creative tab is sorted alphabetically
        event.insertAfter(Items.GHAST_SPAWN_EGG.getDefaultInstance(), GLEEPER_SPAWN_EGG.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }

    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, ID);

    public static final Supplier<EntityType<Gleeper>> GLEEPER = ENTITY_TYPES.register(
            "gleeper",
            () -> EntityType.Builder.of(Gleeper::new, MobCategory.MONSTER).sized(0.6f, 1.5f).clientTrackingRange(8).eyeHeight(1.25f)
                    .build("gleeful_creepers:gleeper")
    );

    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ID);

    public static final DeferredItem<DeferredSpawnEggItem> GLEEPER_SPAWN_EGG = ITEMS.register(
            "gleeper_spawn_egg",
            () -> new DeferredSpawnEggItem(GLEEPER, 0x70922d, 0xfd87cf, new Item.Properties())
    );

    public static ResourceLocation at(String path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path);
    }
}