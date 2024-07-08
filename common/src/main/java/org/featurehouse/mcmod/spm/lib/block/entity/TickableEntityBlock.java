package org.featurehouse.mcmod.spm.lib.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.featurehouse.mcmod.spm.util.tick.ITickable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class TickableEntityBlock<E extends BlockEntity & ITickable> extends BaseEntityBlock {
    protected TickableEntityBlock(Properties properties) {
        super(properties);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide() ? null :
                createTickerHelper(pBlockEntityType, getBlockEntityType(), ITickable::iTick);
    }

    public abstract BlockEntityType<E> getBlockEntityType();

    @NotNull
    @Override
    public abstract E newBlockEntity(BlockPos pPos, BlockState pState);
}
