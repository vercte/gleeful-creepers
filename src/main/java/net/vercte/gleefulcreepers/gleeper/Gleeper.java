package net.vercte.gleefulcreepers.gleeper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.sensing.Sensing;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.entity.animal.feline.Ocelot;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableFeaturePlacerBlock;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.gameevent.*;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.IShearable;
import net.neoforged.neoforge.common.Tags;
import net.vercte.gleefulcreepers.GleefulConfig;
import net.vercte.gleefulcreepers.GleefulSounds;
import net.vercte.gleefulcreepers.GleefulTags;
import net.vercte.gleefulcreepers.gleeper.goal.GleeperTargetPlayersGoal;
import net.vercte.gleefulcreepers.gleeper.goal.MeleeChaseGoal;
import net.vercte.gleefulcreepers.gleeper.goal.SiezeGoal;
import net.vercte.gleefulcreepers.gleeper.goal.SmileGoal;
import net.vercte.gleefulcreepers.gleeper.goal.SwellGoal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.vercte.gleefulcreepers.util.access.LivingEntityAccessor;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

public class Gleeper extends Monster implements IShearable {
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(30, 40);

    private static final EntityDataAccessor<Integer> DATA_SWELL_DIR = SynchedEntityData.defineId(Gleeper.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_IS_IGNITED = SynchedEntityData.defineId(Gleeper.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_SEIZED = SynchedEntityData.defineId(Gleeper.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_ANGERED = SynchedEntityData.defineId(Gleeper.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_SHEARED = SynchedEntityData.defineId(Gleeper.class, EntityDataSerializers.BOOLEAN);

    private int oldSwell = 0;
    private int swell = 0;
    private int maxSwell = 30;

    private int angerTimeMax = 0;
    private int angerTime = 0;

    @Nullable private EntityReference<LivingEntity> angerTarget;

    private final Listener listener = new Listener();
    private final DynamicGameEventListener<Listener> dynamicListener = new DynamicGameEventListener<>(listener);

    public Gleeper(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    public void tick() {
        if (this.isAlive()) {
            if(level().isClientSide() && this.isSiezed() && level().getGameTime() % 6 == 0) {
                double x = this.random.nextGaussian() * 0.02;
                double z = this.random.nextGaussian() * 0.02;
                this.level().addParticle(ParticleTypes.SPLASH, this.getRandomX(0.5), this.getY() + 1.7, this.getRandomZ(0.5), x, 0, z);
            }

            updateAnger();

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

    private void updateAnger() {
        LivingEntity angerTarget = getAngerTargetEntity();
        if(angerTarget == null) return;

        boolean invalid = isInvalidTarget(angerTarget);
        if(!invalid && getSensing().hasLineOfSight(angerTarget) && angerTime > 0) {
            angerTime = angerTimeMax;
            return;
        }

        if(angerTime > 0) angerTime -= isSiezed() ? 3 : 1;
        if(angerTime <= 0 || invalid) {
            angerTime = 0;
            setAngerTarget(null);
            setAngered(false);
        }
    }

    private void explodeCreeper() {
        if (!this.level().isClientSide() && level() instanceof ServerLevel serverLevel) {
            this.dead = true;
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), GleefulConfig.EXPLOSION_RADIUS.get(), GleefulConfig.getExplosionLevel());
            ((ServerLevel)level()).sendParticles(ParticleTypes.SPORE_BLOSSOM_AIR, this.getX(), this.getY() + 1, this.getZ(), 48, 0, 0,0, 1);

            if(GleefulConfig.EXPLOSION_CREATES_FLORA.get()) ((BonemealableFeaturePlacerBlock)Blocks.MOSS_BLOCK).performBonemeal((ServerLevel)level(), getRandom(), blockPosition().below(), Blocks.MOSS_BLOCK.defaultBlockState());

            this.spawnLingeringCloud();
            this.triggerOnDeathMobEffects(serverLevel, RemovalReason.KILLED);
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
            cloud.setPotionDurationScale(0.25F);
            cloud.setDuration(300);
            cloud.setRadiusPerTick(-cloud.getRadius() / cloud.getDuration());

            for(MobEffectInstance instance : collection) {
                cloud.addEffect(new MobEffectInstance(instance));
            }

            this.level().addFreshEntity(cloud);
        }
    }

    @NotNull
    protected InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.is(ItemTags.CREEPER_IGNITERS)) {
            SoundEvent soundevent = stack.is(Items.FIRE_CHARGE) ? SoundEvents.FIRECHARGE_USE : SoundEvents.FLINTANDSTEEL_USE;
            this.level().playSound(player, this.getX(), this.getY(), this.getZ(), soundevent, this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
            if (!this.level().isClientSide()) {
                this.ignite();
                if (!stack.isDamageableItem()) {
                    stack.shrink(1);
                } else {
                    stack.hurtAndBreak(1, player, hand.asEquipmentSlot());
                }
            }

            return InteractionResult.SUCCESS;
        }

        if(stack.is(Tags.Items.FERTILIZERS) && isSheared()) {
            this.level().playSound(player, this.getX(), this.getY(), this.getZ(), SoundEvents.BONE_MEAL_USE, this.getSoundSource(), 1.0F, 1.0F);

            if(this.level().isClientSide()) {
                for(int i = 0; i < 5; ++i) {
                    double xv = getRandom().nextGaussian() * 0.02;
                    double yv = getRandom().nextGaussian() * 0.02;
                    double zv = getRandom().nextGaussian() * 0.02;
                    double x = getX() - 0.5 + getRandom().nextDouble();
                    double y = getY() + 1 + getRandom().nextDouble() * 0.5;
                    double z = getZ() - 0.5 + getRandom().nextDouble();
                    level().addParticle(ParticleTypes.HAPPY_VILLAGER, x, y, z, xv, yv, zv);
                }
            }

            if(!this.level().isClientSide()) {
                if(getRandom().nextInt(3) == 0) setSheared(false);
                if (!stack.isDamageableItem()) {
                    stack.shrink(1);
                } else {
                    stack.hurtAndBreak(1, player, hand.asEquipmentSlot());
                }
            }

            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new SiezeGoal<>(this, Ocelot.class, 6));
        this.goalSelector.addGoal(2, new SiezeGoal<>(this, Cat.class, 6));
        this.goalSelector.addGoal(3, new SwellGoal(this));
        this.goalSelector.addGoal(3, new SmileGoal(this));
        this.goalSelector.addGoal(4, new MeleeChaseGoal(this, 1, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new GleeperTargetPlayersGoal(this));
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

    public void setAngered(boolean angered) {
        this.entityData.set(DATA_ANGERED, angered);
    }

    public boolean isAngered() {
        return this.entityData.get(DATA_ANGERED);
    }

    public void setSheared(boolean sheared) {
        this.entityData.set(DATA_SHEARED, sheared);
    }

    public boolean isSheared() {
        return this.entityData.get(DATA_SHEARED);
    }

    public void startAngerTime(int time) {
        this.angerTimeMax = time;
        this.angerTime = time;
    }

    protected void defineSynchedData(@NotNull SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_SWELL_DIR, 0);
        builder.define(DATA_IS_IGNITED, false);
        builder.define(DATA_SEIZED, false);
        builder.define(DATA_ANGERED, false);
        builder.define(DATA_SHEARED, false);
    }

    public float getSwelling(float pt) {
        return Mth.lerp(pt, (float)this.oldSwell, (float)this.swell) / (float)(this.maxSwell - 2);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.25F).add(Attributes.ATTACK_DAMAGE, 0).add(Attributes.MAX_HEALTH, 12);
    }

    public void setAngerTarget(final @Nullable EntityReference<LivingEntity> target) {
        this.angerTarget = target;
    }

    @Nullable
    public EntityReference<LivingEntity> getAngerTarget() {
        return this.angerTarget;
    }

    @Nullable
    public LivingEntity getAngerTargetEntity() {
        if(angerTarget == null) return null;

        return angerTarget.getEntity(level(), LivingEntity.class);
    }

    @Override
    @Nullable
    public LivingEntity getTarget() {
        if(angerTarget == null) return super.getTarget();
        LivingEntity angerTargetEntity = getAngerTargetEntity();
        return angerTargetEntity == null ? super.getTarget() : angerTargetEntity;
    }

    public boolean isInvalidTarget(LivingEntity entity) {
        return entity.is(GleefulTags.GLEEPER_FORGIVES) || !entity.isAlive() || !this.canAttack(entity);
    }

    @NotNull
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return GleefulSounds.GLEEPER_HURT.get();
    }

    @NotNull
    protected SoundEvent getDeathSound() {
        return GleefulSounds.GLEEPER_DEATH.get();
    }

    @Override
    protected void addAdditionalSaveData(@NotNull ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putShort("Fuse", (short)this.maxSwell);
        output.putBoolean("Ignited", this.isIgnited());
        output.putBoolean("Sheared", this.isSheared());
        output.putInt("AngerTime", angerTime);
        output.putInt("AngerTimeMax", angerTimeMax);
        output.storeNullable("AngerTarget", EntityReference.codec(), this.getAngerTarget());
    }

    @Override
    protected void readAdditionalSaveData(@NotNull ValueInput input) {
        super.readAdditionalSaveData(input);
        this.maxSwell = input.getShortOr("Fuse", (short)30);
        this.setSheared(input.getBooleanOr("Sheared", false));
        this.angerTime = input.getIntOr("AngerTime", 0);
        this.angerTimeMax = input.getIntOr("AngerTimeMax", 0);
        this.setAngerTarget(EntityReference.read(input, "AngerTarget"));
        if (input.getBooleanOr("ignited", false)) {
            this.ignite();
        }
    }

    public static boolean checkGleeperSpawnRules(
            EntityType<? extends Gleeper> type, ServerLevelAccessor level, EntitySpawnReason reason, BlockPos pos, RandomSource random
    ) {
        return level.getDifficulty() != Difficulty.PEACEFUL
                && (EntitySpawnReason.ignoresLightRequirements(reason) || isDarkEnoughToSpawn(level, pos, random))
                && checkMobSpawnRules(type, level, reason, pos, random);
    }

    public static boolean isDarkEnoughToSpawn(ServerLevelAccessor accessor, @NotNull BlockPos pos, RandomSource random) {
        if (accessor.getBrightness(LightLayer.SKY, pos) > random.nextInt(32)) {
            return false;
        } else {
            DimensionType dimensiontype = accessor.dimensionType();
            int defaultLimit = dimensiontype.monsterSpawnBlockLightLimit();
            int lightLimit = Math.min(defaultLimit + 4, 15);
            if (lightLimit < 15 && accessor.getBrightness(LightLayer.BLOCK, pos) > lightLimit) {
                return false;
            } else {
                int j = accessor.getLevel().isThundering() ? accessor.getMaxLocalRawBrightness(pos, 10) : accessor.getMaxLocalRawBrightness(pos);
                return j <= dimensiontype.monsterSpawnLightTest().sample(random);
            }
        }
    }

    @Override
    public void updateDynamicGameEventListener(@NotNull BiConsumer<DynamicGameEventListener<?>, ServerLevel> consumer) {
        if(this.level() instanceof ServerLevel level) consumer.accept(dynamicListener, level);
    }

    @Override
    public boolean isShearable(@Nullable Player player, @NotNull ItemStack item, @NotNull Level level, @NotNull BlockPos pos) {
        return !isSheared();
    }

    @Override
    @NotNull
    public List<ItemStack> onSheared(@Nullable Player player, @NotNull ItemStack item, @NotNull Level level, @NotNull BlockPos pos) {
        this.level().playSound(null, this, SoundEvents.SHEEP_SHEAR, player == null ? SoundSource.BLOCKS : SoundSource.PLAYERS, 1.0F, 1.0F);

        setSheared(true);
        this.setTarget(null);

        return List.of(Items.SPORE_BLOSSOM.getDefaultInstance());
    }

    public class Listener implements GameEventListener {
        private final EntityPositionSource positionSource = new EntityPositionSource(Gleeper.this, Gleeper.this.getEyeHeight());

        @Override
        @NotNull
        public PositionSource getListenerSource() {
            return positionSource;
        }

        @Override
        public int getListenerRadius() {
            return GameEvent.ENTITY_DAMAGE.value().notificationRadius();
        }

        @Override
        public boolean handleGameEvent(@NotNull ServerLevel serverLevel, @NotNull Holder<GameEvent> event, @NotNull GameEvent.Context context, @NotNull Vec3 pos) {
            if(!event.is(GameEvent.ENTITY_DAMAGE.key()) && !event.is(GameEvent.ENTITY_DIE.key())) return false;

            if(!(context.sourceEntity() instanceof LivingEntity victim)) return false;

            LivingEntity offender = ((LivingEntityAccessor)victim).gleeful_creepers$getAttacker();
            if(offender == null) return false;
            if(Gleeper.this.isInvalidTarget(offender)) return false;
            if(Gleeper.this.getAngerTarget() != null && victim != Gleeper.this) return false;

            Sensing sensing = Gleeper.this.getSensing();
            if(!sensing.hasLineOfSight(victim) && !sensing.hasLineOfSight(offender)) return false;

            Gleeper.this.setAngered(true);
            Gleeper.this.setAngerTarget(EntityReference.of(offender));
            Gleeper.this.startAngerTime(PERSISTENT_ANGER_TIME.sample(random));
            Gleeper.this.playSound(GleefulSounds.GLEEPER_ANGER.get(), 1, 0.9f + (random.nextFloat() * 0.2f));

            return true;
        }
    }
}
