package org.featurehouse.mcmod.spm.world.gen.tree;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.grower.AbstractMegaTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.featurehouse.mcmod.spm.SPMMain;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class EnchantedTreeGen extends AbstractMegaTreeGrower {
    @Nullable
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredMegaFeature(Random pRandom) {
        return null;
    }

    @Nullable
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(Random pRandom, boolean pLargeHive) {
        return BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolder(
                ResourceKey.create(Registry.CONFIGURED_FEATURE_REGISTRY,
                        new ResourceLocation(SPMMain.MODID, "enchanted_tree_small")));
    }
}
