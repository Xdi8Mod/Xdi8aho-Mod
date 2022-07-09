package org.featurehouse.mcmod.spm.blocks.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.BeetrootBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.util.tick.RandomTickHelper;

import java.util.Random;

public class EnchantedBeetrootsBlock extends BeetrootBlock {
    public EnchantedBeetrootsBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return SPMMain.ENCHANTED_BEETROOT_SEEDS.get();
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        if (random.nextInt(6) != 0) {   // 5/6 random, instead of 2/3
                                              // is it necessary?
            // super.randomTick
            RandomTickHelper.enchantedCropRandomTick(this, state, world, pos, random);
        }
    }
}
