package top.xdi8.mod.firefly8.block;

import com.mojang.serialization.MapCodec;
import io.github.qwerty770.mcmod.xdi8.lib.blockentity.AbstractBlockWithEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.block.entity.BackPortalCoreBlockEntity;
import top.xdi8.mod.firefly8.block.entity.FireflyBlockEntityTypes;

// TODO org.featurehouse.mcmod.spm.lib.block.entity.TickableEntityBlock
public class BackPortalCoreBlock extends AbstractBlockWithEntity<BackPortalCoreBlockEntity> {
    public static final BooleanProperty IS_VALID = BooleanProperty.create("valid");

    public BackPortalCoreBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(IS_VALID, false));
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(BackPortalCoreBlock::new);
    }

    @Override
    protected boolean blockEntityPredicate(BlockEntity blockEntity) {
        return blockEntity instanceof BackPortalCoreBlockEntity;
    }

    @Override
    protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(IS_VALID);
    }

    @Override
    public @NotNull BlockEntityType<BackPortalCoreBlockEntity> getBlockEntityType() {
        return FireflyBlockEntityTypes.BACK_PORTAL_CORE.get();
    }

    @Override
    public @NotNull BackPortalCoreBlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new BackPortalCoreBlockEntity(pPos, pState);
    }
}
