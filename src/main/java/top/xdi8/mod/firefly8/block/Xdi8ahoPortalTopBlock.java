package top.xdi8.mod.firefly8.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import top.xdi8.mod.firefly8.block.structure.Xdi8PortalBasicData;

import java.util.OptionalInt;

public class Xdi8ahoPortalTopBlock extends Block {
    public static final IntegerProperty FIREFLY_COUNT =
            IntegerProperty.create("fireflies", 0, 5);
    static final int PORTAL_MIN_HEIGHT = 2, PORTAL_MAX_HEIGHT = 16;

    public Xdi8ahoPortalTopBlock() {
        super(Properties.of(Material.STONE)
                .strength(8F, 800F)//TODO
                .requiresCorrectToolForDrops()
                .lightLevel(bs -> bs.getValue(FIREFLY_COUNT) * 3)
        );
        this.registerDefaultState(this.getStateDefinition().any().setValue(FIREFLY_COUNT, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FIREFLY_COUNT);
    }

    /** When placing the torchlight, checking the base & the pillar */
    static OptionalInt getPortalHeight(BlockGetter level, BlockPos thisPos, boolean checkBasement) {
        int heightCount = 0;
        for (int y = thisPos.getY() - 1 ;; y--) {
            if (heightCount > 16) return OptionalInt.empty();

            final BlockPos thatPos = thisPos.atY(y);
            final BlockState blockState = level.getBlockState(thatPos);
            if (blockState.is(FireflyBlockTags.PORTAL_CORE)) {
                if (heightCount < PORTAL_MIN_HEIGHT) return OptionalInt.empty();
                if (checkBasement) {
                    return isPortalBaseValid(level, thatPos) ?
                            OptionalInt.of(heightCount) : OptionalInt.empty();
                } else {
                    return level.getBlockState(thatPos.below(3)).is(FireflyBlockTags.PORTAL_BASE_3) ?
                            OptionalInt.of(heightCount) : OptionalInt.empty();
                }
            } else if (blockState.is(FireflyBlockTags.CENTER_PILLAR)) {
                heightCount++;
            } else return OptionalInt.empty();
        }
    }

    private static boolean isPortalBaseValid(BlockGetter level, BlockPos corePos) {
        return Xdi8PortalBasicData.getInstance().fits(level, corePos);
    }

    private static void fillPortalBlocks(LevelWriter level, BlockPos topPos,
                                         @Range(from = PORTAL_MIN_HEIGHT, to = PORTAL_MAX_HEIGHT)
                                         int portalHeight) {
        for (int i = 1; i <= portalHeight; i++) {
            BlockPos pos = topPos.atY(topPos.getY() - i);
            level.setBlock(pos, FireflyBlocks.XDI8AHO_PORTAL_BLOCK.get().defaultBlockState(), 3);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onPlace(@NotNull BlockState pState, @NotNull Level pLevel,
                        @NotNull BlockPos pPos, @NotNull BlockState pOldState, boolean pIsMoving) {
        if (pLevel.isClientSide()) return;
        if (pState.getValue(FIREFLY_COUNT) <= 0) return;
        final OptionalInt portalHeight = getPortalHeight(pLevel, pPos, true);
        if (portalHeight.isPresent()) {
            fillPortalBlocks(pLevel, pPos, portalHeight.getAsInt());
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(@NotNull BlockState pState, @NotNull Level pLevel,
                         @NotNull BlockPos pPos, @NotNull BlockState pNewState, boolean pIsMoving) {
        // TODO: portal entrance & gen
        final OptionalInt height = getPortalHeight(pLevel, pPos, false);
        if (height.isEmpty()) { // invalid portal
            for (int i = 1; i <= 16; i++) {
                BlockPos thatPos = pPos.atY(pPos.getY() - i);
                BlockState thatState = pLevel.getBlockState(thatPos);
                if (thatState.is(FireflyBlocks.XDI8AHO_PORTAL_BLOCK.get())) {
                    pLevel.setBlockAndUpdate(pPos, Blocks.AIR.defaultBlockState());
                } else break;
            }
        }
    }
}
