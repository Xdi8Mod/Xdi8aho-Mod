package top.xdi8.mod.firefly8.block.symbol;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.featurehouse.mcmod.spm.blocks.SeedUpdaterBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.xdi8.mod.firefly8.screen.Xdi8TableMenu;
import top.xdi8.mod.firefly8.stats.FireflyStats;

/** @see SeedUpdaterBlock */
public class Xdi8TableBlock extends Block {
    public Xdi8TableBlock() {
        super(Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_GRAY)
                .requiresCorrectToolForDrops()
                .strength(3.5F, 6.0F)
        );
    }

    @Nullable
    @Override
    @SuppressWarnings("deprecation")
    public MenuProvider getMenuProvider(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos) {
        return new SimpleMenuProvider((id, inv, player) ->
                new Xdi8TableMenu(id, inv, ContainerLevelAccess.create(pLevel, pPos)), TITLE);
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull InteractionResult use(@NotNull BlockState pState,
                                          @NotNull Level pLevel,
                                          @NotNull BlockPos pPos,
                                          @NotNull Player pPlayer,
                                          @NotNull InteractionHand pHand,
                                          @NotNull BlockHitResult pHit) {
        if (pLevel.isClientSide()) return InteractionResult.SUCCESS;
        pPlayer.openMenu(pState.getMenuProvider(pLevel, pPos));
        pPlayer.awardStat(FireflyStats.INTERACT_WITH_XDI8_TABLE.get());
        return InteractionResult.CONSUME;
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return SHAPE;
    }

    protected static final VoxelShape SHAPE = Block.box(
            0.0D, 0.0D, 0.0D,
            16.0D, 12.0D, 16.0D
    );
    private static final TranslatableComponent TITLE = new TranslatableComponent("menu.firefly8.xdi8_table");
}
