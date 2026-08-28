package net.vercte.gleefulcreepers.creeper;

import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;

public class GleeperTargetPlayersGoal extends NearestAttackableTargetGoal<Player> {
    public GleeperTargetPlayersGoal(Gleeper gleeper) {
        super(gleeper, Player.class, true);
    }

    @Override
    public boolean canUse() {
        return !((Gleeper)this.mob).isSheared() && super.canUse();
    }
}
