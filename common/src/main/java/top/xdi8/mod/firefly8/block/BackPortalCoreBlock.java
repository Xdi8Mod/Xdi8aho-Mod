package top.xdi8.mod.firefly8.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.featurehouse.mcmod.spm.lib.block.entity.TickableEntityBlock;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.block.entity.BackPortalCoreBlockEntity;
import top.xdi8.mod.firefly8.block.entity.FireflyBlockEntityTypes;

public class BackPortalCoreBlock extends TickableEntityBlock<BackPortalCoreBlockEntity> {
    public static final BooleanProperty IS_VALID = BooleanProperty.create("valid");
    public BackPortalCoreBlock() {
        super(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_GRAY)
                .strength(10F, 800F)
                .requiresCorrectToolForDrops());
        this.registerDefaultState(this.defaultBlockState().setValue(IS_VALID, false));
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
