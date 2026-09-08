package net.vercte.gleefulcreepers.gleeper.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.vercte.gleefulcreepers.gleeper.Gleeper;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class SiezeGoal<T extends LivingEntity> extends Goal {
    protected final Gleeper creeper;
    protected final Class<T> avoidClass;
    @Nullable protected T toAvoid;
    protected final float maxDist;
    private final TargetingConditions avoidEntityTargeting;

    public SiezeGoal(Gleeper mob, Class<T> avoidClass, float maxDist) {
        this.creeper = mob;
        this.avoidClass = avoidClass;
        this.maxDist = maxDist;
        this.avoidEntityTargeting = TargetingConditions.forCombat().range(maxDist);
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.TARGET)); // Flag.TARGET is just here so seizing up from cats stops the creeper from swelling
    }

    @Override
    public boolean canUse() {
        this.toAvoid = getServerLevel(this.creeper)
                .getNearestEntity(
                        this.creeper.level().getEntitiesOfClass(
                                this.avoidClass,
                                this.creeper.getBoundingBox()
                                        .inflate(this.maxDist, 3, this.maxDist),
                                _ -> true
                        ),
                        this.avoidEntityTargeting,
                        this.creeper,
                        this.creeper.getX(), this.creeper.getY(), this.creeper.getZ()
                );

        return this.toAvoid != null;
    }

    @Override
    public void start() {
        this.creeper.getNavigation().stop();
        this.creeper.setSiezed(true);
    }

    @Override
    public void stop() {
        this.toAvoid = null;
        this.creeper.setSiezed(false);
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        if(this.toAvoid != null && this.toAvoid.isAlive()) {
            this.creeper.getLookControl().setLookAt(this.toAvoid.getX(), this.toAvoid.getEyeY(), this.toAvoid.getZ());
        }
    }
}
