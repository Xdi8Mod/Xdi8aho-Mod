package top.xdi8.mod.firefly8.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.TeleportTransition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.xdi8.mod.firefly8.world.FireflyTeleportHelper;

/**
 * @see net.minecraft.world.level.block.NetherPortalBlock
 */
public class Xdi8ahoPortalBlock extends Block implements Portal {
    public Xdi8ahoPortalBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
        if (level.isClientSide()) return;
        if (entity.canUsePortal(false)) {
            entity.setAsInsidePortal(this, pos);
        }
    }

    @Override
    @NotNull
    public BlockState updateShape(@NotNull BlockState blockState, @NotNull LevelReader levelReader,
                                  @NotNull ScheduledTickAccess scheduledTickAccess, @NotNull BlockPos blockPos,
                                  @NotNull Direction direction, @NotNull BlockPos blockPos2,
                                  @NotNull BlockState blockState2, @NotNull RandomSource randomSource) {
        if (direction.getAxis() == Direction.Axis.Y) {
            if (!blockState2.is(FireflyBlocks.XDI8AHO_PORTAL_BLOCK.get()) &&
                    !blockState2.is(FireflyBlocks.XDI8AHO_PORTAL_TOP_BLOCK.get()) &&
                    !blockState2.is(FireflyBlockTags.PORTAL_CORE.tagKey())) {
                return Blocks.AIR.defaultBlockState();
            }
        }
        return super.updateShape(blockState, levelReader, scheduledTickAccess, blockPos, direction, blockPos2, blockState2, randomSource);
    }

    @Override
    public int getPortalTransitionTime(@NotNull ServerLevel level, @NotNull Entity entity) {
        return entity instanceof Player player ? player.getAbilities().invulnerable ? 0 : 20 : 0;
    }

    @Nullable
    @Override
    public TeleportTransition getPortalDestination(@NotNull ServerLevel serverLevel, @NotNull Entity entity, @NotNull BlockPos blockPos) {
        return FireflyTeleportHelper.teleportToXdi8aho(serverLevel, entity);
    }

    @Override
    public @NotNull Transition getLocalTransition() {
        return Transition.CONFUSION;
    }
}
