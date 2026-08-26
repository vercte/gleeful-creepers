package net.vercte.gleefulcreepers.creeper;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import org.jetbrains.annotations.NotNull;

public class MeleeChaseGoal extends MeleeAttackGoal {
    public MeleeChaseGoal(PathfinderMob mob, double d, boolean b) {
        super(mob, d, b);
    }

    @Override
    protected void checkAndPerformAttack(@NotNull LivingEntity entity) {
        // noop
    }
}
