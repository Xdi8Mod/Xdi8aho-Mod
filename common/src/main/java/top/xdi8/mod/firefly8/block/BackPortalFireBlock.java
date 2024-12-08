package top.xdi8.mod.firefly8.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.ext.IPortalCooldownEntity;
import top.xdi8.mod.firefly8.world.BackTeleporterImpl;

import java.util.Random;

public final class BackPortalFireBlock extends Block {
    private static final VoxelShape DOWN_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);

    public BackPortalFireBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull BlockState getStateForPlacement(@NotNull BlockPlaceContext pContext) {
        // Hacked
        return BaseFireBlock.getState(pContext.getLevel(), pContext.getClickedPos());
    }

    @Override
    protected void spawnDestroyParticles(@NotNull Level pLevel, @NotNull Player pPlayer,
                                         @NotNull BlockPos pPos, @NotNull BlockState pState) {}

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel,
                                        @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return DOWN_AABB;
    }

    @Override
    public void animateTick(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, Random pRandom) {
        if (pRandom.nextInt(24) == 0) {
            pLevel.playLocalSound((double)pPos.getX() + 0.5D, (double)pPos.getY() + 0.5D, (double)pPos.getZ() + 0.5D,
                    SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS,
                    1.0F + pRandom.nextFloat(), pRandom.nextFloat() * 0.7F + 0.3F,
                    false);
        }
    }

    @Override
    public void playerWillDestroy(@NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState,
                                  @NotNull Player pPlayer) {
        if (!pLevel.isClientSide()) {
            pLevel.levelEvent(null, 1009, pPos, 0);
        }
        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    @Override
    public void entityInside(@NotNull BlockState pState, @NotNull Level pLevel,
                             @NotNull BlockPos pPos, @NotNull Entity pEntity) {
        if (pLevel.isClientSide()) return;
        BlockState downState = pLevel.getBlockState(pPos.below());
        if (!(downState.is(FireflyBlocks.XDI8AHO_BACK_PORTAL_CORE_BLOCK.get())) ||
                (!downState.getValue(BackPortalCoreBlock.IS_VALID))) return;

        IPortalCooldownEntity entityExt = IPortalCooldownEntity.xdi8$extend(pEntity);
        if (!pEntity.isPassenger() && !pEntity.isVehicle() &&
                pEntity.canChangeDimensions() && !entityExt.xdi8$isOnCooldown()) {
            ServerLevel level = (ServerLevel) pLevel;
            BackTeleporterImpl.teleport(pEntity, level.getServer());
            entityExt.xdi8$resetShortCooldown();
        }
    }
}
