package top.xdi8.mod.firefly8.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.ext.IPortalCooldownEntity;
import top.xdi8.mod.firefly8.world.Xdi8DimensionUtils;

/** @see net.minecraft.world.level.block.NetherPortalBlock */
public class Xdi8ahoPortalBlock extends Block {
    public Xdi8ahoPortalBlock(Properties properties) {
        super(properties);
    }

    public void entityInside(@NotNull BlockState pState, @NotNull Level pLevel,
                             @NotNull BlockPos pPos, @NotNull Entity pEntity) {
        if (pLevel.isClientSide()) return;
        IPortalCooldownEntity entityExt = IPortalCooldownEntity.xdi8$extend(pEntity);
        if (!pEntity.isPassenger() && !pEntity.isVehicle() &&
                pEntity.canChangeDimensions() && !entityExt.xdi8$isOnCooldown()) {
            ServerLevel level = (ServerLevel) pLevel;
            if (pEntity instanceof ServerPlayer player){
                Xdi8DimensionUtils.teleportPlayerToXdi8aho(level, player, pPos);
            }
            else {
                Xdi8DimensionUtils.teleportToXdi8aho(level, pEntity);
            }
            entityExt.xdi8$resetShortCooldown();
        }
    }

    @Override
    @NotNull
    public BlockState updateShape(@NotNull BlockState pState,
                                  @NotNull Direction pDirection, @NotNull BlockState pNeighborState,
                                  @NotNull LevelAccessor pLevel, @NotNull BlockPos pCurrentPos,
                                  @NotNull BlockPos pNeighborPos) {
        if (pDirection.getAxis() == Direction.Axis.Y) {
            if (!pNeighborState.is(FireflyBlocks.XDI8AHO_PORTAL_BLOCK.get()) &&
                !pNeighborState.is(FireflyBlocks.XDI8AHO_PORTAL_TOP_BLOCK.get()) &&
                !pNeighborState.is(FireflyBlockTags.PORTAL_CORE)) {
                return Blocks.AIR.defaultBlockState();
            }
        }
        return pState;
    }
}
