package top.xdi8.mod.firefly8.entity;

import it.unimi.dsi.fastutil.objects.Object2LongLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
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
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.particle.FireflyParticles;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @see net.minecraft.world.entity.animal.Bee
 * @see net.minecraft.world.entity.ambient.Bat
 */
public class FireflyEntity extends PathfinderMob implements FlyingAnimal {
    private int lightTime;
    private final Object2LongMap<UUID> ownerMap = new Object2LongLinkedOpenHashMap<>();
    protected Object2LongMap<UUID> getOwnerMap() { return ownerMap; }

    public static FireflyEntity create(Level level) { return new FireflyEntity(FireflyEntityTypes.FIREFLY.get(), level); }
    
    protected FireflyEntity(EntityType<FireflyEntity> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 20, false);
    }

    public void addOwnerUUID(long outOfBottleTime, @Nullable UUID uuid) {
        this.getOwnerMap().put(uuid, outOfBottleTime);
    }

    @Nonnull
    public Collection<Player> getOwners() {
        return getOwnerMap().keySet().stream()
                .flatMap(uuid -> Optional.ofNullable(level.getPlayerByUUID(uuid)).stream())
                .collect(Collectors.toUnmodifiableSet());
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.FLYING_SPEED, 0.2F).add(Attributes.MOVEMENT_SPEED, 0.3F).add(Attributes.ATTACK_DAMAGE, 2.0D).add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    @Override
    public boolean canBeLeashed(@NotNull Player pPlayer) {
        return false;   // ambient mob
    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos pPos, @NotNull LevelReader pLevel) {
        return pLevel.getBlockState(pPos).isAir() ? 10 : 0;
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        // New Schema, 22 Jul, teddyxlandlee
        pCompound.put("OwnerData", FireflyEntityData.serializeOwners(this.getOwnerMap()));
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("OwnerData", Tag.TAG_LIST)) {
            FireflyEntityData.deserializeOwners(this.getOwnerMap(), pCompound.getList("OwnerData", Tag.TAG_COMPOUND));
        }
    }

    private boolean shouldDamage() {
        if (this.level.isDay()) {
            float brightness = this.getBrightness();
            BlockPos blockpos = new BlockPos(this.getX(), this.getEyeY(), this.getZ());
            return brightness > 0.5F && this.level.canSeeSky(blockpos);
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        final Level level = this.getLevel();
        if (level.isClientSide()) {
            if (this.lightTime-- <= 0 && this.random.nextInt(8) == 0) {
                this.lightTime = 100;
                this.level.addParticle(FireflyParticles.FIREFLY.get(), this.getX(), this.getY(), this.getZ(), 0, 0, 0);
            }
        } else {
            if (this.shouldDamage()) {
                this.hurt(DamageSource.ON_FIRE, 0.5F);
            }
        }

        if (!level.isClientSide()) {
            // optimize owner
            final long gameTime = level.getGameTime();
            if (gameTime % 256 == 0) {  // update each 12.8s - way shorter than an hour
                FireflyEntityData.deleteOutdatedOwners(this.getOwnerMap(), gameTime);
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
