package top.xdi8.mod.firefly8.block.entity;

import io.github.qwerty770.mcmod.xdi8.tick.ITickable;
import io.github.qwerty770.mcmod.xdi8.world.BooleanStateManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import top.xdi8.mod.firefly8.block.BackPortalCoreBlock;
import top.xdi8.mod.firefly8.world.FireflyTeleportHelper;

import java.util.function.Supplier;

public class BackPortalCoreBlockEntity extends BlockEntity implements ITickable {
    private final BooleanStateManager stateManager;

    public BackPortalCoreBlockEntity(BlockPos pos, BlockState state) {
        super(FireflyBlockEntityTypes.BACK_PORTAL_CORE.get(), pos, state);
        this.stateManager = new BooleanStateManager(BackPortalCoreBlock.IS_VALID) {
            public boolean shouldChange(boolean newOne) {
                assert BackPortalCoreBlockEntity.this.level != null;
                return BackPortalCoreBlockEntity.this.level.getBlockState(pos).getValue(property) != newOne;
            }

            @Override
            public void run() {
                assert BackPortalCoreBlockEntity.this.level != null;
                boolean valid = isValid(level, pos);
                if (shouldChange(valid)) {
                    BackPortalCoreBlockEntity.this.level.setBlockAndUpdate(pos,
                            BackPortalCoreBlockEntity.this.level.getBlockState(pos).setValue(property, valid));
                }
            }

            private boolean isValid(Level level, BlockPos pos) {
                BlockPos thatPos = pos.below(3);
                for (Supplier<Block> blockSupplier : FireflyTeleportHelper.BACK_PORTAL_BLOCKS.subList(0, 3)) {
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
            stateManager.run();
        }
    }
}
