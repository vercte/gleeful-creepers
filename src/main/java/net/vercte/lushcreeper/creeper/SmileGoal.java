package net.vercte.lushcreeper.creeper;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Creeper;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class SmileGoal extends Goal {
    private final LushCreeper creeper;
    @Nullable
    private LivingEntity target;

    public SmileGoal(LushCreeper creeper) {
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
        LivingEntity entity = this.creeper.getTarget();
        return entity != null && this.creeper.distanceToSqr(entity) < (double)9.0F;
    }

    @Override
    public void tick() {
        if (this.target != null && this.target.isAlive()) {
            this.creeper.getLookControl().setLookAt(this.target.getX(), this.target.getEyeY(), this.target.getZ());
        }
    }
}
