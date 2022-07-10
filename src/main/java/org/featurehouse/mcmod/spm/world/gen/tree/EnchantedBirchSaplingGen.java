package org.featurehouse.mcmod.spm.world.gen.tree;

import net.minecraft.core.Holder;
import net.minecraft.world.level.block.grower.BirchTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.Random;

public class EnchantedBirchSaplingGen extends BirchTreeGrower {
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(Random random, boolean canSpawnBees) {
        return canSpawnBees ? TreeFeatures.BIRCH_BEES_005 : TreeFeatures.BIRCH;
    }
}
