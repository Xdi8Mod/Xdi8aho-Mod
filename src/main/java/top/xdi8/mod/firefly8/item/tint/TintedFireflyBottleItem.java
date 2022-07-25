package top.xdi8.mod.firefly8.item.tint;

import com.mojang.datafixers.util.Either;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import top.xdi8.mod.firefly8.block.FireflyBlockTags;
import top.xdi8.mod.firefly8.entity.FireflyEntity;
import top.xdi8.mod.firefly8.entity.FireflyEntityData;
import top.xdi8.mod.firefly8.item.FireflyItems;

import java.util.Optional;

public class TintedFireflyBottleItem extends Item {
    private static final Logger LOGGER = LogUtils.getLogger();

    public TintedFireflyBottleItem(Properties pProperties) {
        super(pProperties);
    }
    /* FIREFLY BOTTLING START */
    private static final int MAX_FIREFLY_COUNT = 5;

    @Override
    public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack pStack, @NotNull Player pPlayer, @NotNull LivingEntity pTarget, @NotNull InteractionHand pUsedHand) {
        if (!(pTarget instanceof FireflyEntity firefly)) {
            return InteractionResult.PASS;
        }
        Level level = pPlayer.getLevel();
        if (level.isClientSide()) return InteractionResult.SUCCESS;
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        bottleFirefly(stack, pPlayer, level, firefly);
        return InteractionResult.SUCCESS;
    }

    public static boolean bottleFirefly(@NotNull ItemStack newStack, @NotNull Player pPlayer,
                                        @NotNull Level level,
                                        @NotNull FireflyEntity firefly) {
        final CompoundTag rootTag = newStack.getOrCreateTag();
        ListTag fireflyList;
        if (rootTag.contains("Fireflies", 9))
            fireflyList = rootTag.getList("Fireflies", 10);
        else {
            fireflyList = new ListTag();
            rootTag.put("Fireflies", fireflyList);
        }
        final int prevCount = rootTag.size();
        if (prevCount >= MAX_FIREFLY_COUNT) {
            pPlayer.displayClientMessage(new TranslatableComponent("item.firefly8.tinted_firefly_bottle.too_many"), true);
            return false;
        } else {
            if (firefly.isPassenger())
                firefly.stopRiding();
            if (firefly.isVehicle())
                firefly.ejectPassengers();

            CompoundTag targetTags = new CompoundTag();
            targetTags.putLong("InBottleTime", level.getGameTime());
            FireflyEntityData.saveToTag(targetTags, firefly);
            fireflyList.add(targetTags);

            firefly.remove(Entity.RemovalReason.DISCARDED);
            level.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.BOTTLE_FILL,
                    SoundSource.NEUTRAL, 1.0F, 1.0F);
            return true;
        }
    }

    /* FIREFLY BOTTLING END */
    /* FIREFLY RELEASING START */

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel,
                                                           @NotNull Player pPlayer,
                                                           @NotNull InteractionHand pUsedHand) {
        final BlockState playerStandOn = pLevel.getBlockState(pPlayer.blockPosition().below());
        final ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (playerStandOn.is(FireflyBlockTags.FIREFLIES_CAN_RELEASE)) {
            final BlockPos playerHeadPos = pPlayer.eyeBlockPosition();
            final Optional<BlockPos> airPos = getNearAirPos(pLevel, playerHeadPos);
            if (airPos.isEmpty()) {
                return InteractionResultHolder.pass(itemStack);
            }
            final Either<ItemStack, TranslatableComponent> res = spawnFirefly(
                    pLevel, itemStack, pPlayer, pUsedHand, Vec3.atCenterOf(airPos.get()));
            if (res.left().isPresent())
                return InteractionResultHolder.sidedSuccess(res.left().get(),
                        pLevel.isClientSide());
            if (!pLevel.isClientSide()) {
                res.ifRight(c -> pPlayer.displayClientMessage(c, true));
            }
            return InteractionResultHolder.fail(itemStack);
        } else return InteractionResultHolder.pass(itemStack);
    }

    public static boolean removeFirefly(ItemStack stack) {
        ListTag fireflyList = stack.getOrCreateTag().getList("Fireflies", Tag.TAG_COMPOUND);
        if (fireflyList.isEmpty()) return false;
        fireflyList.remove(fireflyList.size() - 1);
        return true;
    }

    @NotNull
    static Either<ItemStack, TranslatableComponent> spawnFirefly(@NotNull Level level,
                                                                 @NotNull ItemStack stack,
                                                                 @NotNull Player player,
                                                                 @NotNull InteractionHand hand,
                                                                 @NotNull Vec3 spawnPos) {
        if (!level.isClientSide()) {
            ListTag fireflyList = stack.getOrCreateTag().getList("Fireflies", Tag.TAG_COMPOUND);
            if (fireflyList.isEmpty()) {
                player.setItemInHand(hand, new ItemStack(FireflyItems.TINTED_GLASS_BOTTLE.get()));
                return Either.right(new TranslatableComponent("item.firefly8.tinted_firefly_bottle.empty"));
            }
            CompoundTag fireflyTag = fireflyList.getCompound(fireflyList.size() - 1);
            FireflyEntity fireflyEntity = FireflyEntity.create(level);
            fireflyEntity.moveTo(spawnPos);

            FireflyEntityData.loadFromTag(fireflyEntity, fireflyTag);
            final long inBottleTime = fireflyTag.getLong("InBottleTime");
            final long outOfBottleTime = level.getGameTime();
            if (outOfBottleTime - inBottleTime >= FireflyEntityData.CHARGE_TIME) { // 20min
                fireflyEntity.addOwnerUUID(outOfBottleTime, player.getUUID());
            }
            level.addFreshEntity(fireflyEntity);
            fireflyList.remove(fireflyList.size() - 1);
        }
        return Either.left(stack);
    }

    static java.util.Optional<BlockPos> getNearAirPos(Level level, BlockPos blockPos) {
        return ALLOWED_SPAWN_POS.stream().map(t -> t.apply(blockPos))
                .filter(p -> level.getBlockState(p).isAir()).findFirst();
    }

    private static final java.util.List<java.util.function.UnaryOperator<BlockPos>> ALLOWED_SPAWN_POS =
            com.google.common.collect.ImmutableList.of(
                    BlockPos::east, BlockPos::south,
                    BlockPos::west, BlockPos::north,
                    java.util.function.UnaryOperator.identity(),
                    BlockPos::below
            );
    /* FIREFLY RELEASING END */
}
