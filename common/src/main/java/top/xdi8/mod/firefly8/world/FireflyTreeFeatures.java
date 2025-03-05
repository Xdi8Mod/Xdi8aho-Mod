package top.xdi8.mod.firefly8.world;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import io.github.qwerty770.mcmod.xdi8.api.InternalRegistryLogWrapper;

import java.util.Optional;
import java.util.function.Supplier;

import static io.github.qwerty770.mcmod.xdi8.util.registries.RegistryHelper.id;

public class FireflyTreeFeatures {
    // Implemented via data pack in Minecraft 1.21+
    public static final InternalRegistryLogWrapper LOG_WRAPPER = InternalRegistryLogWrapper.firefly8("tree_features");

    public static final ResourceKey<ConfiguredFeature<?, ?>> CEDAR = register("cedar");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_CEDAR = register("mega_cedar");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_CEDAR_PINE = register("mega_cedar_pine");
    public static final Supplier<TreeGrower> CEDAR_TREE_GROWER = () -> new TreeGrower("firefly8_redwood",
            0.4F, Optional.of(MEGA_CEDAR), Optional.of(MEGA_CEDAR_PINE), Optional.of(CEDAR), Optional.empty(), Optional.empty(), Optional.empty());

    private static ResourceKey<ConfiguredFeature<?, ?>> register(String id) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, id(id));
    }
}
