package top.xdi8.mod.firefly8.block.entity;

import io.github.qwerty770.mcmod.xdi8.util.tick.ITickable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import top.xdi8.mod.firefly8.block.FireflyBlockTags;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import top.xdi8.mod.firefly8.block.Xdi8ahoPortalTopBlock;

public class PortalTopBlockEntity extends BlockEntity implements ITickable {
    public PortalTopBlockEntity(BlockPos pos, BlockState state) {
        super(FireflyBlockEntityTypes.PORTAL_TOP.get(), pos, state);
    }

    // Just a ticker
    @Override
    public void tick(Level level, BlockPos pos, BlockState state) {
        // Magic Cube detects every 10 ticks, so, here we go...
        if (!level.isClientSide() && level.getGameTime() % 8 == 0) {
            if (state.getValue(Xdi8ahoPortalTopBlock.FIREFLY_COUNT) > 0)
                Xdi8ahoPortalTopBlock.processPortal(level, pos, false);
        }
    }

    public record PortalStatus(int status, int height) {
        public static final int UNACTIVATED = 0, READY = 1, ACTIVATED = 2;

        @Override
        public int height() {
            return status == UNACTIVATED ? -1 : height;
        }

        public static int ofStateStatus(BlockState state) {
            if (state.is(FireflyBlockTags.CENTER_PILLAR.tagKey())) return READY;
            if (state.is(FireflyBlocks.XDI8AHO_PORTAL_BLOCK.get())) return ACTIVATED;
            return UNACTIVATED;
        }

        public static int mix(int status1, int status2) {
            return status1 != status2 ? UNACTIVATED : status1;
        }

        public static PortalStatus empty() { return new PortalStatus(UNACTIVATED, -1); }
    }
}
