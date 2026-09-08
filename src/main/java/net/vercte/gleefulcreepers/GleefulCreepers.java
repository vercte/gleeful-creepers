package net.vercte.gleefulcreepers;

import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTabOutput;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.fml.config.ModConfig;
import net.vercte.gleefulcreepers.gleeper.Gleeper;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

// TODO gleeper spawning
public class GleefulCreepers implements ModInitializer {
    public static final String ID = "gleeful_creepers";

    @Override
    public void onInitialize() {
        GleefulSounds.loadAndRegister();

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.SPAWN_EGGS).register(this::buildCreativeTabs);
        FabricDefaultAttributeRegistry.register(GLEEPER, Gleeper.createAttributes());

        handleSpawning();

        ConfigRegistry.INSTANCE.register(ID, ModConfig.Type.SERVER, GleefulConfig.SPEC);
    }

    private void handleSpawning() {
        Predicate<BiomeSelectionContext> lushCave = p -> p.getBiomeHolder().is(GleefulTags.GLEEPER_SPAWNS_IN);
        BiPredicate<MobCategory, MobSpawnSettings.SpawnerData> creeper = (m, s) -> s.type().equals(EntityType.CREEPER);

        MobSpawnSettings.SpawnerData gleeper = new MobSpawnSettings.SpawnerData(GLEEPER, 4, 4);

        SpawnPlacements.register(GLEEPER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Gleeper::checkGleeperSpawnRules);
        BiomeModifications.create(at("replace_creepers_in_lush_caves"))
                .add(ModificationPhase.REPLACEMENTS, lushCave, ctx -> {
                    ctx.getMobSpawnSettings().removeSpawns(creeper);
                    ctx.getMobSpawnSettings().addSpawn(MobCategory.MONSTER, gleeper, 100);
                });
    }

    private void buildCreativeTabs(FabricCreativeModeTabOutput entries) {
        // I originally put it after the Creeper spawn egg,
        // but it turns out the Spawn Eggs creative tab is sorted alphabetically
        entries.insertAfter(Items.GHAST_SPAWN_EGG.getDefaultInstance(), GLEEPER_SPAWN_EGG.getDefaultInstance());
    }

    public static final EntityType<Gleeper> GLEEPER = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            at("gleeper"),
            EntityType.Builder.of(Gleeper::new, MobCategory.MONSTER).sized(0.6f, 1.5f).clientTrackingRange(8).eyeHeight(1.25f)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, at("gleeper")))
    );

    public static final SpawnEggItem GLEEPER_SPAWN_EGG = Registry.register(
            BuiltInRegistries.ITEM,
            at("gleeper_spawn_egg"),
            new SpawnEggItem(
                    new Item.Properties()
                            .spawnEgg(GLEEPER)
                            .setId(ResourceKey.create(Registries.ITEM, at("gleeper_spawn_egg")))
            )
    );

    public static Identifier at(String path) {
        return Identifier.fromNamespaceAndPath(ID, path);
    }
}