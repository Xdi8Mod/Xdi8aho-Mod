package top.xdi8.mod.firefly8.item.indium;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.block.FireflyBlocks;

public class IndiumAxeItem extends AxeItem {
    public IndiumAxeItem(Properties pProperties) {
        super(new IndiumTier(), 6.0F, -3.2F, pProperties.durability(54));
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack pStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        if (!super.hurtEnemy(pStack, pTarget, pAttacker)) return false;
        IndiumTier.dropNuggets(pStack, pTarget, pAttacker);
        return true;
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockPos pPos, @NotNull LivingEntity pEntityLiving) {
        if (!super.mineBlock(pStack, pLevel, pState, pPos, pEntityLiving)) return false;
        IndiumTier.dropNuggets(pStack, pLevel, pState, pPos, pEntityLiving);
        return true;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context){
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        Player player = context.getPlayer();
        BlockState blockState = level.getBlockState(blockPos);
        ItemStack itemStack = context.getItemInHand();
        BlockState newState;
        if (blockState.getBlock() != FireflyBlocks.CEDAR_WOOD.get() && blockState.getBlock() != FireflyBlocks.CEDAR_LOG.get()){
            return super.useOn(context);
        }
        else {
            level.playSound(player, blockPos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f, 1.0f);
            if (blockState.getBlock() == FireflyBlocks.CEDAR_WOOD.get()){
                newState = FireflyBlocks.STRIPPED_CEDAR_WOOD.get().defaultBlockState();
            }
            else {
                newState = FireflyBlocks.STRIPPED_CEDAR_LOG.get().defaultBlockState();
            }
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockPos, itemStack);
            }
            level.setBlock(blockPos, newState, 11);
            if (player != null) {
                itemStack.hurtAndBreak(1, player, player2 -> player2.broadcastBreakEvent(context.getHand()));
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }
}
