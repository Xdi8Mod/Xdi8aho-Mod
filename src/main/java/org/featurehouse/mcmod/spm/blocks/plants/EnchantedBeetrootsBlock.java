package org.featurehouse.mcmod.spm.blocks.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.BeetrootBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.util.tick.RandomTickHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class EnchantedBeetrootsBlock extends BeetrootBlock {
    public EnchantedBeetrootsBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.getStateDefinition().any().setValue(BlockStateProperties.AGE_3, 0));
    }

    //@ClientOnly
    @Override
    protected ItemLike getBaseSeedId() {
        return SPMMain.ENCHANTED_CROP_SEEDS.get();
    }

    @Override
    public void randomTick(BlockState state, @NotNull ServerLevel world, BlockPos pos, Random random) {
        RandomTickHelper.enchantedCropRandomTick(this, state, world, pos, random);
    }
}
