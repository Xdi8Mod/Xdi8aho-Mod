package io.github.qwerty770.mcmod.xdi8.util.tick;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;

public final class RandomTickHelper {
    // Update to Minecraft 1.20 -- 2023/12/16  Deleted getCropAge(T, BlockState)
    /**
     * <h3>From: Vanilla-1.20.1 (formerly 1.16.1):
     *             {@link CropBlock}</h3>
     * <p>Because of its protected accessibility, QWERTY770 (formerly teddyxlandlee)
     * copies it into this helper class.</p>
     */
    private static float getAvailableMoisture(Block block, BlockGetter blockGetter, BlockPos blockPos) {
        float f = 1.0F;
        BlockPos blockPos2 = blockPos.below();

        for(int i = -1; i <= 1; ++i) {
            for(int j = -1; j <= 1; ++j) {
                float g = 0.0F;
                BlockState blockState = blockGetter.getBlockState(blockPos2.offset(i, 0, j));
                if (blockState.is(Blocks.FARMLAND)) {
                    g = 1.0F;
                    if (blockState.getValue(FarmBlock.MOISTURE) > 0) {
                        g = 3.0F;
                    }
                }

                if (i != 0 || j != 0) {
                    g /= 4.0F;
                }

                f += g;
            }
        }

        BlockPos blockPos3 = blockPos.north();
        BlockPos blockPos4 = blockPos.south();
        BlockPos blockPos5 = blockPos.west();
        BlockPos blockPos6 = blockPos.east();
        boolean bl = blockGetter.getBlockState(blockPos5).is(block) || blockGetter.getBlockState(blockPos6).is(block);
        boolean bl2 = blockGetter.getBlockState(blockPos3).is(block) || blockGetter.getBlockState(blockPos4).is(block);
        if (bl && bl2) {
            f /= 2.0F;
        } else {
            boolean bl3 = blockGetter.getBlockState(blockPos5.north()).is(block)
                    || blockGetter.getBlockState(blockPos6.north()).is(block)
                    || blockGetter.getBlockState(blockPos6.south()).is(block)
                    || blockGetter.getBlockState(blockPos5.south()).is(block);
            if (bl3) {
                f /= 2.0F;
            }
        }

        return f;
    }

    private RandomTickHelper() {}

    public static <T extends CropBlock> void enchantedCropRandomTick(T instance,
                                              BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (world.getRawBrightness(pos, 0) >= 9) {
            int i = instance.getAge(state);
            if (i < instance.getMaxAge()) {
                float f = getAvailableMoisture(instance, world, pos);
                if (random.nextInt((int) ((int)((25.0F / f) + 1) / 2.5F)) == 0) {
                    world.setBlock(pos, instance.getStateForAge(i + 1), 2);
                }
            }
        }
    }
}
