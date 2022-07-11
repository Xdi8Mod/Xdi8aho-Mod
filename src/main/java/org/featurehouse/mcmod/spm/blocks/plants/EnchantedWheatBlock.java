package org.featurehouse.mcmod.spm.blocks.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.platform.api.ClientOnly;
import org.featurehouse.mcmod.spm.util.tick.RandomTickHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class EnchantedWheatBlock extends CropBlock {
    public EnchantedWheatBlock(Properties settings) {
        super(settings);
    }

    @ClientOnly
    @Override
    protected ItemLike getBaseSeedId() {
        return SPMMain.ENCHANTED_WHEAT_SEEDS.get();
    }

    @Override
    public void randomTick(BlockState state, @NotNull ServerLevel world, BlockPos pos, Random random) {
        RandomTickHelper.enchantedCropRandomTick(this, state, world, pos, random);
    }
}