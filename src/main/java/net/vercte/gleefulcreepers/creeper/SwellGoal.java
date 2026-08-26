package net.vercte.gleefulcreepers.creeper;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class SwellGoal extends Goal {
    private final Gleeper creeper;

    public SwellGoal(Gleeper creeper) {
        this.creeper = creeper;
    }

    @Override
    public void start() {
        creeper.setSwellDir(1);
    }

    @Override
    public void stop() {
        creeper.setSwellDir(-1);
    }

    @Override
    public boolean canUse() {
        LivingEntity entity = this.creeper.getTarget();
        return entity != null && entity == this.creeper.getAngerTargetEntity() && this.creeper.distanceToSqr(entity) < (double)16.0F;
    }
}
