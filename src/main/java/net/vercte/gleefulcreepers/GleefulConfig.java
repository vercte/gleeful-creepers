package net.vercte.gleefulcreepers;

import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeConfigSpec;

public class GleefulConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    static {
        BUILDER.push("gleeper");
    }

    public static final ForgeConfigSpec.IntValue EXPLOSION_RADIUS = BUILDER
            .comment("How large the Gleeper's explosion is.")
            .defineInRange("explosionRadius", 3, 1, 64);

    public static final ForgeConfigSpec.BooleanValue EXPLOSION_DAMAGES_BLOCKS = BUILDER
            .comment("Whether the Gleeper can damage blocks on explosion. Respects mobGriefing")
            .define("explosionDamagesBlocks", true);

    public static Level.ExplosionInteraction getExplosionLevel() {
        return EXPLOSION_DAMAGES_BLOCKS.get() ? Level.ExplosionInteraction.MOB : Level.ExplosionInteraction.NONE;
    }

    public static final ForgeConfigSpec.BooleanValue EXPLOSION_CREATES_FLORA = BUILDER
            .comment("Whether the Gleeper's explosion should plant Lush Cave greenery.")
            .define("explosionCreatesFlora", true);

    public static final ForgeConfigSpec.BooleanValue WALKS_WHILE_SWELLING = BUILDER
            .comment("Whether the Gleeper should still be able to follow its target while swelling for an explosion. Normal creepers don't.")
            .worldRestart()
            .define("walksWhileSwelling", true);

    static final ForgeConfigSpec SPEC = BUILDER.build();
}
