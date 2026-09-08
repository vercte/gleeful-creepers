package net.vercte.gleefulcreepers.util.datagen.assets;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.vercte.gleefulcreepers.GleefulConfig;
import net.vercte.gleefulcreepers.GleefulCreepers;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class LangGen extends FabricLanguageProvider {
    public LangGen(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }


    static final String SOUND_GLEEPER_ANGER = "sound.gleeful_creepers.gleeper_anger";
    static final String SOUND_GLEEPER_HURT = "sound.gleeful_creepers.gleeper_hurt";
    static final String SOUND_GLEEPER_DEATH = "sound.gleeful_creepers.gleeper_death";

    @Override
    public void generateTranslations(@NotNull HolderLookup.Provider registryLookup, TranslationBuilder l) {
        l.add(GleefulCreepers.GLEEPER, "Gleeper");
        l.add(GleefulCreepers.GLEEPER_SPAWN_EGG, "Gleeper Spawn Egg");
        l.add(SOUND_GLEEPER_ANGER, "Gleeper angers");
        l.add(SOUND_GLEEPER_HURT, "Gleeper hurts");
        l.add(SOUND_GLEEPER_DEATH, "Gleeper dies");

        addConfig(l, "gleeper", "Gleeper");
        addConfig(l, GleefulConfig.EXPLOSION_RADIUS, "Explosion Radius");
        addConfig(l, GleefulConfig.EXPLOSION_DAMAGES_BLOCKS, "Explosion Damages Blocks");
        addConfig(l, GleefulConfig.EXPLOSION_CREATES_FLORA, "Explosion Creates Flora");
        addConfig(l, GleefulConfig.WALKS_WHILE_SWELLING, "Walks While Swelling");
    }

    private void addConfig(TranslationBuilder builder, ModConfigSpec.ConfigValue<?> value, String text) {
        addConfig(builder, value.getPath().getLast(), text);
    }

    private void addConfig(TranslationBuilder builder, String path, String text) {
        builder.add(GleefulCreepers.ID + ".configuration." + path, text);
    }
}
