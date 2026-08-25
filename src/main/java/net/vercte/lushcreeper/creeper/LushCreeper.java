package net.vercte.lushcreeper.creeper;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;

public class LushCreeper extends Monster implements NeutralMob {
    private static final EntityDataAccessor<Integer> DATA_SWELL_DIR = SynchedEntityData.defineId(LushCreeper.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_IS_IGNITED = SynchedEntityData.defineId(LushCreeper.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_SEIZED = SynchedEntityData.defineId(LushCreeper.class, EntityDataSerializers.BOOLEAN);
    private int oldSwell = 0;
    private int swell = 0;
    private int maxSwell = 30;

    public LushCreeper(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }


    public void tick() {
        if (this.isAlive()) {
            this.oldSwell = this.swell;

            boolean ignited = this.isIgnited();
            if(ignited) this.setSwellDir(1);

            int swellDir = this.getSwellDir();

            if (swellDir == 1 && this.swell == 0) {
                this.playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.5F);
                this.gameEvent(GameEvent.PRIME_FUSE);
            }

            this.swell += swellDir;
            if (this.swell < 0) {
                this.swell = 0;
            }

            if (this.swell >= this.maxSwell) {
                this.swell = this.maxSwell;
                this.explodeCreeper();
            }
        }

        super.tick();
    }

    private void explodeCreeper() {
        if (!this.level().isClientSide) {
            this.dead = true;
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 3, Level.ExplosionInteraction.MOB);
            this.spawnLingeringCloud();
            this.triggerOnDeathMobEffects(RemovalReason.KILLED);
            this.discard();
        }
    }

    private void spawnLingeringCloud() {
        Collection<MobEffectInstance> collection = this.getActiveEffects();
        if (!collection.isEmpty()) {
            AreaEffectCloud cloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
            cloud.setRadius(2.5F);
            cloud.setRadiusOnUse(-0.5F);
            cloud.setWaitTime(10);
            cloud.setDuration(cloud.getDuration() / 2);
            cloud.setRadiusPerTick(-cloud.getRadius() / (float)cloud.getDuration());

            for(MobEffectInstance instance : collection) {
                cloud.addEffect(new MobEffectInstance(instance));
            }

            this.level().addFreshEntity(cloud);
        }
    }

    @NotNull
    protected InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.is(ItemTags.CREEPER_IGNITERS)) {
            SoundEvent soundevent = itemstack.is(Items.FIRE_CHARGE) ? SoundEvents.FIRECHARGE_USE : SoundEvents.FLINTANDSTEEL_USE;
            this.level().playSound(player, this.getX(), this.getY(), this.getZ(), soundevent, this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
            if (!this.level().isClientSide) {
                this.ignite();
                if (!itemstack.isDamageableItem()) {
                    itemstack.shrink(1);
                } else {
                    itemstack.hurtAndBreak(1, player, getSlotForHand(hand));
                }
            }

            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new SiezeGoal<>(this, Ocelot.class, 6));
        this.goalSelector.addGoal(2, new SiezeGoal<>(this, Cat.class, 6));
        this.goalSelector.addGoal(3, new SmileGoal(this));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

    public void ignite() {
        this.entityData.set(DATA_IS_IGNITED, true);
    }

    public boolean isIgnited() {
        return this.entityData.get(DATA_IS_IGNITED);
    }

    public void setSwellDir(int dir) {
        this.entityData.set(DATA_SWELL_DIR, dir);
    }

    public int getSwellDir() {
        return this.entityData.get(DATA_SWELL_DIR);
    }

    public void setSiezed(boolean seized) {
        this.entityData.set(DATA_SEIZED, seized);
    }

    public boolean isSiezed() {
        return this.entityData.get(DATA_SEIZED);
    }

    protected void defineSynchedData(@NotNull SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_SWELL_DIR, 0);
        builder.define(DATA_IS_IGNITED, false);
        builder.define(DATA_SEIZED, false);
    }

    public float getSwelling(float pt) {
        return Mth.lerp(pt, (float)this.oldSwell, (float)this.swell) / (float)(this.maxSwell - 2);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.25F).add(Attributes.MAX_HEALTH, 10);
    }

    @NotNull
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return SoundEvents.CREEPER_HURT;
    }

    @NotNull
    protected SoundEvent getDeathSound() {
        return SoundEvents.CREEPER_DEATH;
    }

    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);

        tag.putShort("Fuse", (short)this.maxSwell);
        tag.putBoolean("Ignited", this.isIgnited());
    }

    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);

        if(tag.contains("Fuse", 99)) this.maxSwell = tag.getShort("Fuse");
        if(tag.getBoolean("Ignited")) this.ignite();
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return 0;
    }

    @Override
    public void setRemainingPersistentAngerTime(int i) {

    }

    @Override
    public @Nullable UUID getPersistentAngerTarget() {
        return null;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID uuid) {

    }

    @Override
    public void startPersistentAngerTimer() {

    }
}
