package org.featurehouse.mcmod.spm.lib.block.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.featurehouse.mcmod.spm.util.tick.ITickable;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@ApiStatus.Experimental
public abstract class TickableContainerEntityBlock<E extends BlockEntity & ITickable> extends TickableEntityBlock<E> {
    protected abstract boolean blockEntityPredicate(BlockEntity blockEntity);

    protected TickableContainerEntityBlock(Properties settings) {
        super(settings);
    }

    public List<ResourceLocation> incrementWhileOnUse(BlockState state, Level world, BlockPos pos, ServerPlayer serverPlayerEntity, InteractionHand hand, BlockHitResult blockHitResult) {
        return ImmutableList.of();
    }

    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!world.isClientSide) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof MenuProvider && blockEntityPredicate(blockEntity)) {
                player.openMenu((MenuProvider) blockEntity);
                incrementWhileOnUse(state, world, pos, (ServerPlayer) player, hand, hit).forEach(player::awardStat);
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (itemStack.hasCustomHoverName()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof BaseContainerBlockEntity && blockEntityPredicate(blockEntity))
                ((BaseContainerBlockEntity) blockEntity).setCustomName(itemStack.getHoverName());
        }
    }

    @Override
    public MenuProvider getMenuProvider(BlockState state, Level world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity instanceof MenuProvider && blockEntityPredicate(blockEntity) ? (MenuProvider) blockEntity : null;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof Container && blockEntityPredicate(blockEntity)) {
                Containers.dropContents(world, pos, (Container) blockEntity);
                world.updateNeighbourForOutputSignal(pos, this);
            }
        }
        super.onRemove(state, world, pos, newState, moved);
    }

    @Override
    public abstract @NotNull E newBlockEntity(BlockPos pos, BlockState state);

    @SuppressWarnings("deprecation")
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @SuppressWarnings("deprecation")
    public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(world.getBlockEntity(pos));
    }
}
