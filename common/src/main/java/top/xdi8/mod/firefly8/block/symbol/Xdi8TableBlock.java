package top.xdi8.mod.firefly8.block.symbol;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.xdi8.mod.firefly8.screen.Xdi8TableMenu;
import top.xdi8.mod.firefly8.stats.FireflyStats;

// see SeedUpdaterBlock of SPM/SPR
public class Xdi8TableBlock extends Block {
    public Xdi8TableBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public MenuProvider getMenuProvider(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos) {
        return new SimpleMenuProvider((id, inv, player) ->
                new Xdi8TableMenu(id, inv, ContainerLevelAccess.create(pLevel, pPos)), TITLE);
    }

    @Override
    public @NotNull InteractionResult useWithoutItem(@NotNull BlockState pState,
                                          @NotNull Level pLevel,
                                          @NotNull BlockPos pPos,
                                          @NotNull Player pPlayer,
                                          @NotNull BlockHitResult pHitResult) {
        if (pLevel.isClientSide()) return InteractionResult.SUCCESS;
        pPlayer.openMenu(pState.getMenuProvider(pLevel, pPos));
        pPlayer.awardStat(FireflyStats.INTERACT_WITH_XDI8_TABLE.get());
        return InteractionResult.CONSUME;
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return SHAPE;
    }

    protected static final VoxelShape SHAPE = Block.box(
            0.0D, 0.0D, 0.0D,
            16.0D, 12.0D, 16.0D
    );
    private static final MutableComponent TITLE = Component.translatable("menu.firefly8.xdi8_table");
}
