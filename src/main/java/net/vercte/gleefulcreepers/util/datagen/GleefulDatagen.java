package net.vercte.gleefulcreepers.util.datagen;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.entity.EntityType;
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
        CompletableFuture<HolderLookup.Provider> registries = event.getLookupProvider();

        event.addProvider(new ModelGen(output));
        event.addProvider(new LangGen(output));
        event.addProvider(new SoundGen(output));

        event.addProvider(new DatapackBuiltinEntriesProvider(output, event.getLookupProvider(), getRegistrySetBuilder(), Set.of(GleefulCreepers.ID)));

        event.addProvider(new LootGen(output, registries));
        event.addProvider(new EntityTagGen(output, registries));
        event.addProvider(new BiomeTagGen(output, registries));
    }

    public static RegistrySetBuilder getRegistrySetBuilder() {
        return new RegistrySetBuilder()
                .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, bootstrap -> {
                    HolderGetter<Biome> biomes = bootstrap.lookup(Registries.BIOME);

                    bootstrap.register(REMOVE_CREEPERS_FROM_LUSH_CAVES,
                            new BiomeModifiers.RemoveSpawnsBiomeModifier(
                                    biomes.getOrThrow(GleefulTags.GLEEPER_SPAWNS_IN),
                                    HolderSet.direct(EntityType.CREEPER.builtInRegistryHolder())
                            )
                    );

                    MobSpawnSettings.SpawnerData gleeperSpawnerData = new MobSpawnSettings.SpawnerData(GleefulCreepers.GLEEPER.get(), 4, 4);
                    bootstrap.register(ADD_GLEEPERS_TO_LUSH_CAVES,
                            new BiomeModifiers.AddSpawnsBiomeModifier(
                                    biomes.getOrThrow(GleefulTags.GLEEPER_SPAWNS_IN),
                                    WeightedList.<MobSpawnSettings.SpawnerData>builder()
                                            .add(gleeperSpawnerData, 100)
                                            .build()
                            )
                    );
                });
    }
}
