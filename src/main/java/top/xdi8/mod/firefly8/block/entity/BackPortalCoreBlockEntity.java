package top.xdi8.mod.firefly8.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.featurehouse.mcmod.spm.util.BooleanStateManager;
import org.featurehouse.mcmod.spm.util.tick.ITickable;
import top.xdi8.mod.firefly8.block.BackPortalCoreBlock;
import top.xdi8.mod.firefly8.world.Xdi8TeleporterImpl;

import java.util.function.Supplier;

public class BackPortalCoreBlockEntity extends BlockEntity implements ITickable {
    private final BooleanStateManager stateManager;

    public BackPortalCoreBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(FireflyBlockEntityTypes.BACK_PORTAL_CORE.get(), pWorldPosition, pBlockState);
        this.stateManager = new BooleanStateManager(BackPortalCoreBlock.IS_VALID) {
            @Override
            public void run(Level level, BlockPos pos, BlockState oldState) {
                final boolean valid = isValid(level, pos);
                if (shouldChange(valid, oldState)) {
                    level.setBlockAndUpdate(pos, oldState.setValue(property, valid));
                }
            }

            private boolean isValid(Level level, BlockPos pos) {
                BlockPos thatPos = pos.below(3);
                for (Supplier<Block> blockSupplier : Xdi8TeleporterImpl.X2O_PORTAL_BASE) {
                    if (!level.getBlockState(thatPos).is(blockSupplier.get())) return false;
                    thatPos = thatPos.above();
                }
                return true;
            }
        };
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state) {
        if (!level.isClientSide() && level.getGameTime() % 8 == 0) {
            stateManager.run(level, pos, state);
        }
    }
}
