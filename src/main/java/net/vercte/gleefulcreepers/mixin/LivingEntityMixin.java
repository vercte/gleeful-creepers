package net.vercte.gleefulcreepers.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.vercte.gleefulcreepers.util.access.LivingEntityAccessor;

// We specifically need this mixin because apparently Minecraft sets the attacking entity AFTER the game event is called.
// What the fuck! It's messed up. So we have to add that info here
@Mixin(LivingEntity.class)
public class LivingEntityMixin implements LivingEntityAccessor {
    @Unique
    @Nullable
    private LivingEntity gleeful_creepers$attacker;

    @Override
    @Nullable
    public LivingEntity gleeful_creepers$getAttacker() {
        return gleeful_creepers$attacker;
    }

    @Inject(method = "actuallyHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;gameEvent(Lnet/minecraft/core/Holder;)V"))
    public void addAttackerInfo(DamageSource source, float f, CallbackInfo ci) {
        this.gleeful_creepers$attacker = source.getEntity() instanceof LivingEntity living ? living : null;
    }
}
