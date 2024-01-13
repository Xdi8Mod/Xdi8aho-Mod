package top.xdi8.mod.firefly8.block.cedar;

import net.minecraft.core.Holder;
import net.minecraft.world.level.block.grower.AbstractMegaTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class CedarTreeGrower extends AbstractMegaTreeGrower {
    @Nullable
    @Override
    public Holder<? extends ConfiguredFeature<?, ?>> getConfiguredMegaFeature(@NotNull Random random) {
        return random.nextDouble() < 0.6 ? FireflyTreeFeatures.MEGA_CEDAR : FireflyTreeFeatures.MEGA_CEDAR_PINE;
    }

    @Nullable
    @Override
    public Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull Random random, boolean largeHive) {
        return FireflyTreeFeatures.CEDAR;
    }
}
