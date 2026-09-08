package net.vercte.gleefulcreepers.gleeper.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.vercte.gleefulcreepers.gleeper.Gleeper;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class SmileGoal extends Goal {
    private final Gleeper creeper;
    @Nullable
    private LivingEntity target;

    public SmileGoal(Gleeper creeper) {
        this.creeper = creeper;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public void start() {
        this.creeper.getNavigation().stop();
        this.target = this.creeper.getTarget();
    }

    @Override
    public void stop() {
        this.target = null;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    public boolean canUse() {
        LivingEntity entity = creeper.getTarget();
        return !creeper.isSheared() &&
               entity != null &&
               entity != creeper.getAngerTargetEntity() &&
               creeper.distanceToSqr(entity) < (double)9.0F;
    }

    @Override
    public void tick() {
        if (this.target != null && this.target.isAlive()) {
            this.creeper.getLookControl().setLookAt(this.target.getX(), this.target.getEyeY(), this.target.getZ());
        }
    }
}
