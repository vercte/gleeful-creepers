package net.vercte.gleefulcreepers;

import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Monster;
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

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.SPAWN_EGGS).register(this::buildCreativeTabs);
        FabricDefaultAttributeRegistry.register(GLEEPER, Gleeper.createAttributes());

        handleSpawning();

        NeoForgeConfigRegistry.INSTANCE.register(ID, ModConfig.Type.SERVER, GleefulConfig.SPEC);
    }

    private void handleSpawning() {
        Predicate<BiomeSelectionContext> lushCave = p -> p.getBiomeRegistryEntry().is(GleefulTags.GLEEPER_SPAWNS_IN);
        BiPredicate<MobCategory, MobSpawnSettings.SpawnerData> creeper = (m, s) -> s.type.equals(EntityType.CREEPER);

        MobSpawnSettings.SpawnerData gleeper = new MobSpawnSettings.SpawnerData(GLEEPER, 4, 4, 150);

        SpawnPlacements.register(GLEEPER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        BiomeModifications.create(at("replace_creepers_in_lush_caves"))
                .add(ModificationPhase.REPLACEMENTS, lushCave, ctx -> {
                    ctx.getSpawnSettings().removeSpawns(creeper);
                    ctx.getSpawnSettings().addSpawn(MobCategory.MONSTER, gleeper);
                });
    }

    private void buildCreativeTabs(FabricItemGroupEntries entries) {
        // I originally put it after the Creeper spawn egg,
        // but it turns out the Spawn Eggs creative tab is sorted alphabetically
        entries.addAfter(Items.GHAST_SPAWN_EGG.getDefaultInstance(), GLEEPER_SPAWN_EGG.getDefaultInstance());
    }

//    public void replaceCreepers(FinalizeSpawnEvent event) {
//        boolean isNatural = event.getSpawnType().equals(MobSpawnType.NATURAL);
//        if(!isNatural) return;
//
//        boolean isCreeper = event.getEntity().getType().equals(EntityType.CREEPER);
//        if(!isCreeper) return;
//
//        BlockPos pos = event.getEntity().blockPosition();
//        ServerLevelAccessor level = event.getLevel();
//
//        if(!level.getBiome(pos).is(GleefulTags.GLEEPER_SPAWNS_IN)) return;
//
//        GLEEPER.get().spawn(level.getLevel(), pos, MobSpawnType.NATURAL);
//
//        event.setSpawnCancelled(true);
//    }

    public static final EntityType<Gleeper> GLEEPER = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            at("gleeper"),
            EntityType.Builder.of(Gleeper::new, MobCategory.MONSTER).sized(0.6f, 1.5f).clientTrackingRange(8).eyeHeight(1.25f)
                    .build("gleeful_creepers:gleeper")
    );

    public static final SpawnEggItem GLEEPER_SPAWN_EGG = Registry.register(
            BuiltInRegistries.ITEM,
            at("gleeper_spawn_egg"),
            new SpawnEggItem(GLEEPER, 0x70922d, 0xfd87cf, new Item.Properties())
    );

    public static ResourceLocation at(String path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path);
    }
}