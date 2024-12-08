package top.xdi8.mod.firefly8.block.cedar;

import com.google.common.collect.ImmutableList;
import io.github.qwerty770.mcmod.xdi8.api.SimpleStateProviderTool;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaPineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AlterGroundDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import io.github.qwerty770.mcmod.xdi8.api.InternalRegistryLogWrapper;

public class FireflyTreeFeatures {
    public static final InternalRegistryLogWrapper LOG_WRAPPER = InternalRegistryLogWrapper.firefly8("tree_features");
    public static SimpleStateProviderTool CEDAR_LOG = new SimpleStateProviderTool(FireflyBlocks.CEDAR_LOG);
    public static SimpleStateProviderTool CEDAR_LEAVES = new SimpleStateProviderTool(FireflyBlocks.CEDAR_LEAVES);
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> CEDAR = FeatureUtils.register("cedar",
            Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(CEDAR_LOG,
                    new StraightTrunkPlacer(7, 3, 2), CEDAR_LEAVES,
                    new SpruceFoliagePlacer(UniformInt.of(2, 3), UniformInt.of(0, 2), UniformInt.of(1, 2)),
                    new TwoLayersFeatureSize(2, 0, 2)).ignoreVines().build());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> MEGA_CEDAR = FeatureUtils.register("mega_cedar",
            Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(CEDAR_LOG,
                    new GiantTrunkPlacer(15, 2, 16), CEDAR_LEAVES,
                    new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(13, 17)),
                    new TwoLayersFeatureSize(1, 1, 2))
                    .decorators(ImmutableList.of(new AlterGroundDecorator(BlockStateProvider.simple(Blocks.PODZOL)))).build());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> MEGA_CEDAR_PINE = FeatureUtils.register("mega_cedar_pine",
            Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(CEDAR_LOG,
                    new GiantTrunkPlacer(15, 2, 16), CEDAR_LEAVES,
                    new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(3, 7)),
                    new TwoLayersFeatureSize(1, 1, 2))
                    .decorators(ImmutableList.of(new AlterGroundDecorator(BlockStateProvider.simple(Blocks.PODZOL)))).build());
}
