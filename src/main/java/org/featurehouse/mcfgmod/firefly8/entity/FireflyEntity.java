package org.featurehouse.mcfgmod.firefly8.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import org.featurehouse.mcfgmod.firefly8.particle.FireflyParticles;
import org.jetbrains.annotations.NotNull;

/**
 * @see net.minecraft.world.entity.animal.Bee
 * @see net.minecraft.world.entity.ambient.Bat
 */
public class FireflyEntity extends PathfinderMob implements FlyingAnimal {
    //private static final EntityDataAccessor<Byte> DATA_LUMINANCE
    //        = SynchedEntityData.defineId(FireflyEntity.class, EntityDataSerializers.BYTE);
    private int tickTime;

    protected FireflyEntity(EntityType<FireflyEntity> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.FLYING_SPEED, 0.2F)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    @Override
    public boolean canBeLeashed(@NotNull Player pPlayer) {
        return false;   // ambient mob
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        //this.getEntityData().define(DATA_LUMINANCE, (byte)0);
    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos pPos, @NotNull LevelReader pLevel) {
        return pLevel.getBlockState(pPos).isAir() ? 10 : 0;
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        //pCompound.put("Luminance", luminance)
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level.getGameTime() % 128 == 0) {  // TODO: random tick + non-dup check
            this.level.addParticle(FireflyParticles.FIREFLY.get(), this.getX(), this.getY(), this.getZ(),
                    0, 0, 0);
        }
    }

    @Override
    protected void customServerAiStep() {
        //TODO
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
}
