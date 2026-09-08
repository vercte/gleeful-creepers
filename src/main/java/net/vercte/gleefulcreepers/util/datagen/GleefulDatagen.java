package net.vercte.gleefulcreepers.util.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.vercte.gleefulcreepers.util.datagen.assets.ItemModelGen;
import net.vercte.gleefulcreepers.util.datagen.assets.LangGen;
import net.vercte.gleefulcreepers.util.datagen.assets.SoundGen;
import net.vercte.gleefulcreepers.util.datagen.data.BiomeTagGen;
import net.vercte.gleefulcreepers.util.datagen.data.EntityTagGen;
import net.vercte.gleefulcreepers.util.datagen.data.LootGen;

import java.util.concurrent.CompletableFuture;

public class GleefulDatagen {
    public static void gatherData(GatherDataEvent event) {
        PackOutput output = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> registries = event.getLookupProvider();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        event.addProvider(new ItemModelGen(output, fileHelper));
        event.addProvider(new LangGen(output));
        event.addProvider(new SoundGen(output, fileHelper));

        event.addProvider(new LootGen(output, registries));
        event.addProvider(new EntityTagGen(output, registries, fileHelper));
        event.addProvider(new BiomeTagGen(output, registries, fileHelper));
    }
}
