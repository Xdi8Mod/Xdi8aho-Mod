package org.featurehouse.mcmod.spm.world.gen.tree;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.featurehouse.mcmod.spm.SPMMain;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class GrassDecorator extends TreeDecorator {
    public static final Codec<GrassDecorator> CODEC = Codec.unit(GrassDecorator::new);

    @Override
    protected @NotNull TreeDecoratorType<?> type() {
        return SPMMain.GRASS_DECORATOR_TYPE.get();
    }

    @Override
    public void place(LevelSimulatedReader pLevel,
                      BiConsumer<BlockPos, BlockState> pBlockSetter,
                      Random pRandom,
                      List<BlockPos> pLogPositions,
                      List<BlockPos> pLeafPositions) {
        if (!pLogPositions.isEmpty()) {
            pLogPositions.stream().min(Comparator.comparingInt(BlockPos::getY))
                    .ifPresent(pos -> {
                        for (int i = 0; i < 7; i++) {
                            final int j = pRandom.nextInt(64);
                            var calcPos = calcPos(j, pos);
                            if (pLevel.isStateAtPosition(calcPos.below(), state -> state.is(Blocks.GRASS_BLOCK)) &&
                                    pLevel.isStateAtPosition(calcPos, BlockBehaviour.BlockStateBase::isAir)) {
                                boolean canBeTall = pLevel.isStateAtPosition(calcPos.above(), BlockBehaviour.BlockStateBase::isAir);
                                if (canBeTall && pRandom.nextBoolean()) pBlockSetter.accept(calcPos, TALL_GRASS);
                                else pBlockSetter.accept(calcPos, GRASS);
                            }
                        }
                    });
        }
    }

    private static BlockPos calcPos(int i, BlockPos p) {
        int x = XZ[(i >> 4) & 0b11];
        int z = XZ[i & 0b11];
        int y = (i >> 2) & 0b11;
        return p.offset(x, y, z);
    }
    private static final int[] XZ = {-2, -1, 1, 2};
    private static final BlockState GRASS = Blocks.GRASS.defaultBlockState(),
                                    TALL_GRASS = Blocks.TALL_GRASS.defaultBlockState();
}
