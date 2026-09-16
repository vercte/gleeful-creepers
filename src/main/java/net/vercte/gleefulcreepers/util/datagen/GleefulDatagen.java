package net.vercte.gleefulcreepers.util.datagen;

import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.vercte.gleefulcreepers.GleefulCreepers;
import net.vercte.gleefulcreepers.GleefulTags;
import net.vercte.gleefulcreepers.util.datagen.assets.ModelGen;
import net.vercte.gleefulcreepers.util.datagen.assets.LangGen;
import net.vercte.gleefulcreepers.util.datagen.assets.SoundGen;
import net.vercte.gleefulcreepers.util.datagen.data.BiomeTagGen;
import net.vercte.gleefulcreepers.util.datagen.data.EntityTagGen;
import net.vercte.gleefulcreepers.util.datagen.data.LootGen;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class GleefulDatagen {
    private static final ResourceKey<BiomeModifier> REMOVE_CREEPERS_FROM_LUSH_CAVES = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, GleefulCreepers.at("remove_creepers_from_lush_caves"));
    private static final ResourceKey<BiomeModifier> ADD_GLEEPERS_TO_LUSH_CAVES = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, GleefulCreepers.at("add_gleepers_to_lush_caves"));

    public static void gatherData(GatherDataEvent.Client event) {
        PackOutput output = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> world = event.getWorldLookupProvider();
        CompletableFuture<HolderLookup.Provider> reloadable = event.getReloadableLookupProvider();

        event.addProvider(new ModelGen(output));
        event.addProvider(new LangGen(output));
        event.addProvider(new SoundGen(output));

        event.addProvider(
                DatapackBuiltinEntriesProvider.forWorldLayer(
                        output,
                        "GleefulCreepersWorld",
                        world,
                        getWorldRegistrySetBuilder(),
                        Set.of(GleefulCreepers.ID)
                )
        );

        event.addProvider(
                DatapackBuiltinEntriesProvider.forReloadableLayer(
                        output,
                        "GleefulCreepersReloadable",
                        world,
                        reloadable,
                        getReloadableRegistrySetBuilder(),
                        Set.of(GleefulCreepers.ID)
                )
        );

        event.addProvider(new EntityTagGen(output, world));
        event.addProvider(new BiomeTagGen(output, world));
    }

    public static RegistrySetBuilder getWorldRegistrySetBuilder() {
        return new RegistrySetBuilder()
                .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, bootstrap -> {
                    HolderGetter<Biome> biomes = bootstrap.lookup(Registries.BIOME);

                    bootstrap.register(REMOVE_CREEPERS_FROM_LUSH_CAVES,
                            new BiomeModifiers.RemoveSpawnsBiomeModifier(
                                    biomes.getOrThrow(GleefulTags.GLEEPER_SPAWNS_IN),
                                    HolderSet.direct(EntityTypes.CREEPER.builtInRegistryHolder())
                            )
                    );

                    MobSpawnSettings.SpawnerData gleeperSpawnerData = new MobSpawnSettings.SpawnerData(GleefulCreepers.GLEEPER.get(), new ConstantInt(4));
                    bootstrap.register(ADD_GLEEPERS_TO_LUSH_CAVES,
                            new BiomeModifiers.AddSpawnsBiomeModifier(
                                    biomes.getOrThrow(GleefulTags.GLEEPER_SPAWNS_IN),
                                    WeightedList.<MobSpawnSettings.SpawnerData>builder()
                                            .add(gleeperSpawnerData, 200)
                                            .build()
                            )
                    );
                });
    }

    public static RegistrySetBuilder getReloadableRegistrySetBuilder() {
        return new RegistrySetBuilder()
                .add(Registries.LOOT_TABLE, new LootGen());
    }
}
