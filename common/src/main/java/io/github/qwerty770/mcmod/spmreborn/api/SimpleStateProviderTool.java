package io.github.qwerty770.mcmod.spmreborn.api;

import dev.architectury.registry.registries.RegistrySupplier;
import io.github.qwerty770.mcmod.spmreborn.util.annotation.StableApi;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import org.jetbrains.annotations.NotNull;

@StableApi
public class SimpleStateProviderTool extends SimpleStateProvider {
    // This class is created to avoid NullPointerException "Registry Object not present".
    // For the usage of this, see io.github.qwerty770.mcmod.spmreborn.world.tree.SweetPotatoTreeFeatures (also used in Xdi8Aho Mod 1.21+)
    private final RegistrySupplier<Block> blockSupplier;

    public SimpleStateProviderTool(RegistrySupplier<Block> supplier) {
        super(Blocks.AIR.defaultBlockState());  // placeholder
        this.blockSupplier = supplier;
    }

    @Override
    public @NotNull BlockState getState(@NotNull RandomSource random, @NotNull BlockPos pos) {
        return this.blockSupplier.get().defaultBlockState();
    }
}
