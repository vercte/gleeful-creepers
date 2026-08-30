package net.vercte.gleefulcreepers.gleeper.goal;

import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.vercte.gleefulcreepers.gleeper.Gleeper;

public class GleeperTargetPlayersGoal extends NearestAttackableTargetGoal<Player> {
    public GleeperTargetPlayersGoal(Gleeper gleeper) {
        super(gleeper, Player.class, true);
    }

    @Override
    public boolean canUse() {
        return !((Gleeper)this.mob).isSheared() && super.canUse();
    }
}
