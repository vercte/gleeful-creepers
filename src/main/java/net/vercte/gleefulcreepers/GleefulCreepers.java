package net.vercte.gleefulcreepers;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.vercte.gleefulcreepers.client.GleefulCreepersClient;
import net.vercte.gleefulcreepers.gleeper.Gleeper;
import net.vercte.gleefulcreepers.util.datagen.GleefulDatagen;

import java.util.function.Supplier;

@Mod(GleefulCreepers.ID)
public class GleefulCreepers {
    public static final String ID = "gleeful_creepers";

    public GleefulCreepers(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();

        ENTITY_TYPES.register(bus);
        ITEMS.register(bus);

        GleefulSounds.loadAndRegister(bus);

        bus.addListener(this::registerEntityAttributes);
        bus.addListener(this::registerSpawnPlacements);
        bus.addListener(this::buildCreativeTabs);

        bus.addListener(GleefulCreepersClient::setup);
        bus.addListener(GleefulCreepersClient::registerLayers);
        bus.addListener(GleefulDatagen::gatherData);

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, GleefulConfig.SPEC);
    }

    private void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(GLEEPER.get(), Gleeper.createAttributes().build());
    }

    private void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(
                GLEEPER.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Gleeper::checkGleeperSpawnRules,
                SpawnPlacementRegisterEvent.Operation.REPLACE
        );
    }

    private void buildCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() != CreativeModeTabs.SPAWN_EGGS) return;
        // I originally put it after the Creeper spawn egg,
        // but it turns out the Spawn Eggs creative tab is sorted alphabetically
        event.getEntries().putAfter(Items.GHAST_SPAWN_EGG.getDefaultInstance(), GLEEPER_SPAWN_EGG.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }

    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ID);

    public static final Supplier<EntityType<Gleeper>> GLEEPER = ENTITY_TYPES.register(
            "gleeper",
            () -> EntityType.Builder.of(Gleeper::new, MobCategory.MONSTER).sized(0.6f, 1.5f).clientTrackingRange(8)
                    .build("gleeful_creepers:gleeper")
    );

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ID);

    public static final Supplier<ForgeSpawnEggItem> GLEEPER_SPAWN_EGG = ITEMS.register(
            "gleeper_spawn_egg",
            () -> new ForgeSpawnEggItem(GLEEPER, 0x70922d, 0xfd87cf, new Item.Properties())
    );

    public static ResourceLocation at(String path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path);
    }
}