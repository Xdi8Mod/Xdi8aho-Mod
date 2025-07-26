package top.xdi8.mod.firefly8.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class RedwoodHangingSignBlockEntity extends HangingSignBlockEntity {
    public RedwoodHangingSignBlockEntity(BlockPos pos, BlockState blockState) {
        super(pos, blockState);
    }

    @Override
    public @NotNull BlockEntityType<?> getType() {
        return FireflyBlockEntityTypes.REDWOOD_HANGING_SIGN.get();
    }

    @Override
    public boolean isValidBlockState(@NotNull BlockState state) {
        return FireflyBlockEntityTypes.REDWOOD_HANGING_SIGN.get().isValid(state);
    }
}
