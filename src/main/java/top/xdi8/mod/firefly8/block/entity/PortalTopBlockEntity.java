package top.xdi8.mod.firefly8.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.featurehouse.mcmod.spm.util.tick.ITickable;
import top.xdi8.mod.firefly8.block.Xdi8ahoPortalTopBlock;

import java.util.OptionalInt;

public class PortalTopBlockEntity extends BlockEntity implements ITickable {
    public PortalTopBlockEntity(BlockPos pos, BlockState state) {
        super(FireflyBlockEntityTypes.PORTAL_TOP.get(), pos, state);
    }

    // Just a ticker
    @Override
    public void tick(Level level, BlockPos pos, BlockState state) {
        if (!level.isClientSide() && level.getGameTime() % 64 == 0) {
            final OptionalInt portalHeight = Xdi8ahoPortalTopBlock.getPortalHeight(level, pos, false);
            if (portalHeight.isPresent()) {
                Xdi8ahoPortalTopBlock.fillPortalBlocks(level, pos, portalHeight.getAsInt());
            } else {
                Xdi8ahoPortalTopBlock.removePortal(level, pos);
            }
        }
    }
}
