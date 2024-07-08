package top.xdi8.mod.firefly8.item.tint;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.entity.FireflyEntity;
import top.xdi8.mod.firefly8.item.FireflyItems;

import javax.annotation.Nullable;
import java.util.List;

public class TintedGlassBottleItem extends BottleItem {
    public TintedGlassBottleItem(Properties p_40648_) {
        super(p_40648_);
    }

    @Override
    protected @NotNull ItemStack turnBottleIntoItem(final @NotNull ItemStack pBottleStack,
                                                    final @NotNull Player pPlayer,
                                                    @NotNull ItemStack pFilledBottleStack) {
        if (ItemTinting.shouldTint(pFilledBottleStack)) {
            pFilledBottleStack = ItemTinting.tint(pFilledBottleStack);
        }
        return super.turnBottleIntoItem(pBottleStack, pPlayer, pFilledBottleStack);
    }

    /* HONEY START */

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos blockPos = pContext.getClickedPos();
        BlockState state = level.getBlockState(blockPos);
        if (!state.is(BlockTags.BEEHIVES) || !(state.hasProperty(BeehiveBlock.HONEY_LEVEL))) {
            return super.useOn(pContext);
        }
        int honeyLevel = state.getValue(BeehiveBlock.HONEY_LEVEL);
        if (honeyLevel >= 5) {
            Player player = pContext.getPlayer();
            if (player == null) return super.useOn(pContext);
            InteractionHand hand = pContext.getHand();
            ItemStack stack = player.getItemInHand(hand);

            stack.shrink(1);
            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL,
                    SoundSource.NEUTRAL, 1.0F, 1.0F);
            if (stack.isEmpty()) {
                player.setItemInHand(hand, new ItemStack(FireflyItems.TINTED_HONEY_BOTTLE.get()));
            } else if (!player.getInventory().add(new ItemStack(FireflyItems.TINTED_HONEY_BOTTLE.get()))) {
                player.drop(new ItemStack(FireflyItems.TINTED_HONEY_BOTTLE.get()), false);
            }
            level.gameEvent(player, GameEvent.FLUID_PICKUP, blockPos);

            if (!level.isClientSide()) {
                player.awardStat(Stats.ITEM_USED.get(this));
                if (!CampfireBlock.isSmokeyPos(level, blockPos)) {
                    if (hiveContainsBees(level, blockPos))
                        angerNearbyBees(level, blockPos);
                    releaseBeesAndResetHoneyLevel(level, state, blockPos, player);
                } else resetHoneyLevel(level, state, blockPos);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.useOn(pContext);
    }

    /* M J S B : These non-statics can be static */

    private static boolean hiveContainsBees(Level pLevel, BlockPos pPos) {
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        if (blockentity instanceof BeehiveBlockEntity beehiveblockentity) {
            return !beehiveblockentity.isEmpty();
        } else {
            return false;
        }
    }

    protected static void releaseBeesAndResetHoneyLevel(Level pLevel, BlockState pState, BlockPos pPos, @Nullable Player pPlayer) {
        resetHoneyLevel(pLevel, pState, pPos);
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        if (blockentity instanceof BeehiveBlockEntity beehiveblockentity) {//inlined
            beehiveblockentity.emptyAllLivingFromHive(pPlayer, pState, BeehiveBlockEntity.BeeReleaseStatus.EMERGENCY);
        }

    }

    protected static void resetHoneyLevel(Level pLevel, BlockState pState, BlockPos pPos) {
        pLevel.setBlock(pPos, pState.setValue(BeehiveBlock.HONEY_LEVEL, 0), 3);
    }

    private static void angerNearbyBees(Level pLevel, BlockPos pPos) {
        List<Bee> list = pLevel.getEntitiesOfClass(Bee.class, (new AABB(pPos)).inflate(8.0D, 6.0D, 8.0D));
        if (!list.isEmpty()) {
            List<Player> list1 = pLevel.getEntitiesOfClass(Player.class, (new AABB(pPos)).inflate(8.0D, 6.0D, 8.0D));
            if (list1.isEmpty()) return; //Forge: Prevent Error when no players are around.
            int i = list1.size();

            for (Bee bee : list) {
                if (bee.getTarget() == null) {
                    bee.setTarget(list1.get(pLevel.random.nextInt(i)));
                }
            }
        }
    }

    /* HONEY END */
    /* FIREFLY BOTTLING START */

    @Override
    public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack pStack, @NotNull Player pPlayer, @NotNull LivingEntity pTarget, @NotNull InteractionHand pUsedHand) {
        if (!(pTarget instanceof FireflyEntity firefly)){
            return InteractionResult.PASS;
        }
        Level level = pPlayer.getLevel();
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        ItemStack resultStack = new ItemStack(FireflyItems.TINTED_FIREFLY_BOTTLE.get(), 1);
        if (TintedFireflyBottleItem.bottleFirefly(resultStack, pPlayer, level, firefly)) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                pPlayer.setItemInHand(pUsedHand, resultStack);
            } else if (!pPlayer.getInventory().add(resultStack)) {
                pPlayer.drop(resultStack, false);
            }
        }
        return InteractionResult.SUCCESS;
    }

    /* FIREFLY BOTTLING END */
}
