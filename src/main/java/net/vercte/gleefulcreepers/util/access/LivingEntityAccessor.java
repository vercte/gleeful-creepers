package net.vercte.gleefulcreepers.util.access;

import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public interface LivingEntityAccessor {
    @Nullable LivingEntity gleeful_creepers$getAttacker();
}
