package top.xdi8.mod.firefly8.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.RandomSource;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.particle.FireflyParticles;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @see net.minecraft.world.entity.animal.Bee
 * @see net.minecraft.world.entity.ambient.Bat
 */
public class FireflyEntity extends PathfinderMob implements FlyingAnimal, OwnableEntity {
    private final RandomSource randomSource = new XoroshiroRandomSource(this.random.nextLong(), this.random.nextLong());
    private int lightTime;
    /* NBT */
    private boolean isNaturalGen = true;    // kept true for compatibility
    // TODO more than 1 owner
    protected static final EntityDataAccessor<Optional<UUID>> DATA_OWNERUUID_ID = SynchedEntityData.defineId(FireflyEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private long inBottleTime;
    private long outOfBottleTime;

    protected FireflyEntity(EntityType<FireflyEntity> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 20, false);
    }

    public void setNaturalGen(boolean naturalGen) {
        isNaturalGen = naturalGen;
    }

    public boolean isNaturalGen() {
        return isNaturalGen;
    }

    public void setInBottleTime(long time) {
        inBottleTime = time;
    }

    public long getInBottleTime() {
        return inBottleTime;
    }

    public void setOutOfBottleTime(long time) {
        outOfBottleTime = time;
    }

    public long getOutOfBottleTime() {
        return outOfBottleTime;
    }

    @Nullable
    @Override
    public UUID getOwnerUUID() {
        return this.entityData.get(DATA_OWNERUUID_ID).orElse(null);
    }

    public void setOwnerUUID(@Nullable UUID p_21817_) {
        this.entityData.set(DATA_OWNERUUID_ID, Optional.ofNullable(p_21817_));
    }

    @Nullable
    @Override
    public Player getOwner() {
        try {
            UUID uuid = this.getOwnerUUID();
            return uuid == null ? null : this.level.getPlayerByUUID(uuid);
        } catch (IllegalArgumentException illegalargumentexception) {
            return null;
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.FLYING_SPEED, 0.2F).add(Attributes.MOVEMENT_SPEED, 0.3F).add(Attributes.ATTACK_DAMAGE, 2.0D).add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    @Override
    public boolean canBeLeashed(@NotNull Player pPlayer) {
        return false;   // ambient mob
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_OWNERUUID_ID, Optional.empty());
        //this.getEntityData().define(DATA_LUMINANCE, (byte)0);
    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos pPos, @NotNull LevelReader pLevel) {
        return pLevel.getBlockState(pPos).isAir() ? 10 : 0;
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("NaturalGen", isNaturalGen());
        pCompound.putLong("InBottleTime", getInBottleTime());
        pCompound.putLong("OutOfBottleTime", getOutOfBottleTime());
        // net.minecraft.world.entity.TamableAnimal
        if (this.getOwnerUUID() != null) {
            pCompound.putUUID("Owner", this.getOwnerUUID());
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setNaturalGen(pCompound.getBoolean("NaturalGen"));
        this.setInBottleTime(pCompound.getLong("InBottleTime"));
        this.setOutOfBottleTime(pCompound.getLong("OutOfBottleTime"));

        // net.minecraft.world.entity.TamableAnimal
        UUID uuid;
        if (pCompound.hasUUID("Owner")) {
            uuid = pCompound.getUUID("Owner");
        } else {
            String owner = pCompound.getString("Owner");
            uuid = OldUsersConverter.convertMobOwnerIfNecessary(Objects.requireNonNull(this.getServer()),
                    owner);
        }

        if (uuid != null) {
            try {
                this.setOwnerUUID(uuid);
            } catch (Throwable ignored) {
            }
        }
    }

    private boolean shouldDamage() {
        if (this.level.isDay() && !this.isNaturalGen) {
            float brightness = this.getBrightness();
            BlockPos blockpos = new BlockPos(this.getX(), this.getEyeY(), this.getZ());
            return brightness > 0.5F && this.level.canSeeSky(blockpos);
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getLevel().isClientSide()) {
            if (this.lightTime-- <= 0 && this.randomSource.nextInt(8) == 0) {
                this.lightTime = 100;
                this.level.addParticle(FireflyParticles.FIREFLY.get(), this.getX(), this.getY(), this.getZ(), 0, 0, 0);
            }
        } else {
            if (this.shouldDamage()) {
                this.hurt(DamageSource.ON_FIRE, 0.5F);
            }
        }
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    public boolean isPushable() {
        return false;
    }

    protected void doPush(@NotNull Entity pEntity) {
    }

    protected void pushEntities() {
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new FleeSunGoal(this, 1.0));
        this.goalSelector.addGoal(4, new AbstractFollowPlayerGoal.FollowOwner(this));
        this.goalSelector.addGoal(5, new AbstractFollowPlayerGoal.Randomly(this));
        this.goalSelector.addGoal(5, new Wandering());
        this.goalSelector.addGoal(5, new FloatGoal(this));
        // this.goalSelector.addGoal(2, new RestrictSunGoal(this));
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level pLevel) {
        var navigation = new FlyingPathNavigation(this, pLevel);
        navigation.setCanOpenDoors(false);
        navigation.setCanFloat(false);
        navigation.setCanPassDoors(true);
        return navigation;
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pPos, @NotNull BlockState pBlock) {
        // NO-OP: flying
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource pDamageSource) {
        return null;    // NO-OP
    }

    @Override
    protected SoundEvent getDeathSound() {
        return null;    // NO-OP, currently
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, @NotNull DamageSource pSource) {
        return false;
    }

    @Override
    protected void checkFallDamage(double pY, boolean pOnGround, @NotNull BlockState pState, @NotNull BlockPos pPos) {
        // NO-OP
    }

    @Override
    public boolean isFlying() {
        return !this.isOnGround();
    }

    @Override
    public @NotNull MobType getMobType() {
        return super.getMobType();
    }

    protected void jumpInLiquid(@NotNull TagKey<Fluid> pFluidTag) {
        this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.01D, 0.0D));
    }

    class Wandering extends Goal {

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return FireflyEntity.this.navigation.isDone() && FireflyEntity.this.random.nextInt(10) == 0;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return FireflyEntity.this.navigation.isInProgress();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            Vec3 vec3 = this.findPos();
            if (vec3 != null) {
                FireflyEntity.this.navigation.moveTo(FireflyEntity.this.navigation.createPath(new BlockPos(vec3), 1), 0.3D);
            }

        }

        @Nullable
        private Vec3 findPos() {
            Vec3 vec3 = FireflyEntity.this.getViewVector(0.0F);
            Vec3 vec32 = HoverRandomPos.getPos(FireflyEntity.this, 8, 7, vec3.x, vec3.z, ((float) Math.PI / 2F), 3, 1);
            return vec32 != null ? vec32 : AirAndWaterRandomPos.getPos(FireflyEntity.this, 8, 4, -2, vec3.x, vec3.z, (float) Math.PI / 2F);
        }
    }
}
