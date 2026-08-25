package net.vercte.gleefulcreepers.util.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.vercte.gleefulcreepers.util.datagen.assets.ItemModelGen;
import net.vercte.gleefulcreepers.util.datagen.assets.LangGen;

public class GleefulDatagen {
    public static void gatherData(GatherDataEvent event) {
        PackOutput output = event.getGenerator().getPackOutput();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        event.addProvider(new ItemModelGen(output, fileHelper));
        event.addProvider(new LangGen(output));
    }
}
