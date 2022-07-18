package org.featurehouse.mcmod.spm.world.gen.tree;

import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.platform.api.reg.PlatformRegister;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TreeFeatures2 {
    public static void activateMe() {}

    @SuppressWarnings("all")
    static @NotNull Holder<ConfiguredFeature<?, ?>> register(String id, TreeConfiguration conf) {
        return BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_FEATURE,
                PlatformRegister.id(id), new ConfiguredFeature<>(Feature.TREE, conf));
    }

    public static final Holder<ConfiguredFeature<?, ?>> ENCHANTED_TREE_SMALL =
            register("enchanted_tree_small",new TreeConfiguration.TreeConfigurationBuilder(
                    /*trunk_provider*/SimpleStateProvider.simple(Blocks.SPRUCE_LOG),
                    /*trunk_placer*/new StraightTrunkPlacer(5, 2, 1),
                    /*foliage_provider*/new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(SPMMain.ENCHANTED_LEAVES.get().defaultBlockState(), 1).build()),
                    /*foliage_placer*/new SpruceFoliagePlacer(/*radius*/UniformInt.of(2, 3), /*offset*/UniformInt.of(0, 2), /*trunk_height*/UniformInt.of(1, 2)),
                    /*minimum_size*/new TwoLayersFeatureSize(2, 0, 2)
            )
                    .decorators(List.of(new GrassDecorator()))
                    .ignoreVines()
                    .build()
            );
}
