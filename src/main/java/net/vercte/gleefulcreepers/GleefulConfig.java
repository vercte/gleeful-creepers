package net.vercte.gleefulcreepers;

import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.ModConfigSpec;

public class GleefulConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    static {
        BUILDER.push("gleeper");
    }

    public static final ModConfigSpec.IntValue EXPLOSION_RADIUS = BUILDER
            .comment("How large the Gleeper's explosion is.")
            .defineInRange("explosionRadius", 3, 1, 64);

    public static final ModConfigSpec.BooleanValue EXPLOSION_DAMAGES_BLOCKS = BUILDER
            .comment("Whether the Gleeper can damage blocks on explosion. Respects mobGriefing")
            .define("explosionDamagesBlocks", true);

    public static Level.ExplosionInteraction getExplosionLevel() {
        return EXPLOSION_DAMAGES_BLOCKS.get() ? Level.ExplosionInteraction.MOB : Level.ExplosionInteraction.NONE;
    }

    public static final ModConfigSpec.BooleanValue EXPLOSION_CREATES_FLORA = BUILDER
            .comment("Whether the Gleeper's explosion should plant Lush Cave greenery.")
            .define("explosionCreatesFlora", true);

    public static final ModConfigSpec.BooleanValue WALKS_WHILE_SWELLING = BUILDER
            .comment("Whether the Gleeper should still be able to follow its target while swelling for an explosion. Normal creepers don't.")
            .worldRestart()
            .define("walksWhileSwelling", false);

    static final ModConfigSpec SPEC = BUILDER.build();
}
