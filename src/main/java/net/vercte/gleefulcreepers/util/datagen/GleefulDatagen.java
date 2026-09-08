package net.vercte.gleefulcreepers.util.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.vercte.gleefulcreepers.util.datagen.assets.LangGen;
import net.vercte.gleefulcreepers.util.datagen.assets.ModelGen;
import net.vercte.gleefulcreepers.util.datagen.data.BiomeTagGen;
import net.vercte.gleefulcreepers.util.datagen.data.EntityTagGen;
import net.vercte.gleefulcreepers.util.datagen.data.LootGen;

public class GleefulDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        pack.addProvider(ModelGen::new);
        pack.addProvider(LangGen::new);

        pack.addProvider(LootGen::new);
        pack.addProvider(EntityTagGen::new);
        pack.addProvider(BiomeTagGen::new);
    }
}
