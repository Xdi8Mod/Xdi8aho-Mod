package top.xdi8.mod.firefly8.item.tint;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.block.FireflyBlockTags;
import top.xdi8.mod.firefly8.entity.FireflyEntity;
import top.xdi8.mod.firefly8.entity.FireflyEntityData;
import top.xdi8.mod.firefly8.item.FireflyDataComponentTypes;
import top.xdi8.mod.firefly8.item.FireflyItems;
import top.xdi8.mod.firefly8.stats.FireflyStats;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.UnaryOperator;

public class TintedFireflyBottleItem extends Item {
    public TintedFireflyBottleItem(Properties pProperties) {
        super(pProperties);
    }

    /* FIREFLY BOTTLING START */
    private static final int MAX_FIREFLY_COUNT = 5;

    private static ListTag getFireflies(ItemStack stack) {
        var FIREFLIES = FireflyDataComponentTypes.FIREFLIES.get();
        if (!stack.has(FIREFLIES) || Objects.requireNonNull(stack.get(FIREFLIES)).isEmpty()) {
            CompoundTag tag = new CompoundTag();
            tag.put("Fireflies", new ListTag());
            stack.applyComponents(DataComponentMap.builder().set(FIREFLIES, CustomData.of(tag)).build());
        }
        CompoundTag fireflyTag = Objects.requireNonNull(stack.get(FIREFLIES)).copyTag();
        return fireflyTag.getList("Fireflies", Tag.TAG_COMPOUND);
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack stack, @NotNull Player player,
                                                           @NotNull LivingEntity interactionTarget, @NotNull InteractionHand usedHand) {
        if (!(interactionTarget instanceof FireflyEntity firefly)) {
            return InteractionResult.PASS;
        }
        Level level = player.level();
        if (level.isClientSide()) return InteractionResult.SUCCESS;
        bottleFirefly(player.getItemInHand(usedHand), player, level, firefly);
        return InteractionResult.SUCCESS;
    }

    public static boolean bottleFirefly(@NotNull ItemStack stack, @NotNull Player player,
                                        @NotNull Level level, @NotNull FireflyEntity firefly) {
        var FIREFLIES = FireflyDataComponentTypes.FIREFLIES.get();
        ListTag fireflyList = getFireflies(stack);
        final int prevCount = fireflyList.size();
        if (prevCount >= MAX_FIREFLY_COUNT) {
            player.displayClientMessage(Component.translatable("item.firefly8.tinted_firefly_bottle.too_many"), true);
            return false;
        } else {
            firefly.unRide();
            CompoundTag targetTags = new CompoundTag();
            targetTags.putLong("InBottleTime", level.getGameTime());
            FireflyEntityData.saveToTag(targetTags, firefly);
            fireflyList.add(targetTags);

            CompoundTag tag = new CompoundTag();
            tag.put("Fireflies", fireflyList);
            stack.applyComponents(DataComponentMap.builder().set(FIREFLIES, CustomData.of(tag)).build());

            firefly.remove(Entity.RemovalReason.DISCARDED);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL,
                    SoundSource.NEUTRAL, 1.0F, 1.0F);
            player.awardStat(FireflyStats.FIREFLIES_CAUGHT.get());
            return true;
        }
    }

    /* FIREFLY BOTTLING END */
    /* FIREFLY RELEASING START */

    @Override
    public @NotNull InteractionResult use(@NotNull Level level, @NotNull Player player,
                                          @NotNull InteractionHand hand) {
        final BlockState playerStandOn = level.getBlockState(player.blockPosition().below());
        final ItemStack oldStack = player.getItemInHand(hand);
        if (playerStandOn.is(FireflyBlockTags.FIREFLIES_CAN_RELEASE.tagKey())) {
            final BlockPos playerHeadPos = player.blockPosition();
            final Optional<BlockPos> airPos = getNearAirPos(level, playerHeadPos);
            if (airPos.isEmpty())  return InteractionResult.PASS;
            final Either<ItemStack, MutableComponent> res = spawnFirefly(
                    level, oldStack, player, hand, Vec3.atCenterOf(airPos.get()));
            if (res.left().isPresent()) {
                ItemStack newStack = res.left().get();
                oldStack.consume(1, player);
                if (oldStack.isEmpty()) player.setItemInHand(hand, newStack);
                else if (!player.getInventory().add(newStack)) {
                    player.drop(newStack, false);
                }
                player.awardStat(FireflyStats.FIREFLIES_RELEASED.get());
                return InteractionResult.SUCCESS.heldItemTransformedTo(newStack);
            }
            if (res.right().isPresent() && !level.isClientSide()) {
                res.ifRight(c -> player.displayClientMessage(c, true));
            }
            return InteractionResult.FAIL;
        } else return InteractionResult.PASS;
    }

    public static boolean removeFirefly(ItemStack stack) {
        var fireflyData = stack.get(FireflyDataComponentTypes.FIREFLIES.get());
        if (fireflyData == null) return false;
        ListTag fireflyList = fireflyData.copyTag().getList("Fireflies", Tag.TAG_COMPOUND);
        if (fireflyList.isEmpty()) return false;
        fireflyList.removeLast();
        return true;
    }

    @NotNull
    static Either<ItemStack, MutableComponent> spawnFirefly(@NotNull Level level,
                                                            @NotNull ItemStack stack,
                                                            @NotNull Player player,
                                                            @NotNull InteractionHand hand,
                                                            @NotNull Vec3 spawnPos) {
        if (level.isClientSide()) return Either.left(stack);
        var FIREFLIES = FireflyDataComponentTypes.FIREFLIES.get();
        ListTag fireflyList = getFireflies(stack);
        if (fireflyList.isEmpty()) {
            player.setItemInHand(hand, new ItemStack(FireflyItems.TINTED_GLASS_BOTTLE.get()));
            return Either.right(Component.translatable("item.firefly8.tinted_firefly_bottle.empty"));
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
        fireflyList.removeLast();
        if (fireflyList.isEmpty()) {
            ItemStack stack1 = new ItemStack(FireflyItems.TINTED_GLASS_BOTTLE.get(), 1);
            return Either.left(stack1);
        }
        else {
            CompoundTag tag = new CompoundTag();
            tag.put("Fireflies", fireflyList);
            stack.applyComponents(DataComponentMap.builder().set(FIREFLIES, CustomData.of(tag)).build());
            return Either.left(stack);
        }
    }

    static java.util.Optional<BlockPos> getNearAirPos(Level level, BlockPos blockPos) {
        return ALLOWED_SPAWN_POS.stream().map(t -> t.apply(blockPos))
                .filter(p -> level.getBlockState(p).isAir()).findFirst();
    }

    private static final List<UnaryOperator<BlockPos>> ALLOWED_SPAWN_POS =
            com.google.common.collect.ImmutableList.of(
                    BlockPos::east, BlockPos::south,
                    BlockPos::west, BlockPos::north,
                    java.util.function.UnaryOperator.identity(),
                    BlockPos::below
            );
    /* FIREFLY RELEASING END */
}
