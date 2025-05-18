package top.xdi8.mod.firefly8.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class RedwoodSignBlockEntity extends SignBlockEntity {
    public RedwoodSignBlockEntity(BlockPos pos, BlockState blockState) {
        super(FireflyBlockEntityTypes.REDWOOD_SIGN.get(), pos, blockState);
    }
}
