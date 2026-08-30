package net.vercte.gleefulcreepers.creeper;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.vercte.gleefulcreepers.GleefulConfig;

import java.util.EnumSet;

public class SwellGoal extends Goal {
    private final Gleeper creeper;

    public SwellGoal(Gleeper creeper) {
        this.creeper = creeper;

        EnumSet<Flag> flags = EnumSet.of(Flag.TARGET); // this is just here so seizing up from cats stops the creeper from swelling
        if(!GleefulConfig.WALKS_WHILE_SWELLING.get()) flags.add(Flag.MOVE);
        this.setFlags(flags);
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
        LivingEntity target = this.creeper.getTarget();
        return target != null && target == this.creeper.getAngerTargetEntity() &&
                this.creeper.distanceToSqr(target) < (double)16.0F &&
                this.creeper.getSensing().hasLineOfSight(target);
    }
}
