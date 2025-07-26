package top.xdi8.mod.firefly8.forge.datagen;

import com.google.common.collect.Streams;
import io.github.qwerty770.mcmod.xdi8.registries.RegistryHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.ModDataGen;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import top.xdi8.mod.firefly8.block.symbol.SymbolStoneBlock;
import top.xdi8.mod.firefly8.core.letters.LettersUtil;

import java.util.Set;
import java.util.function.Supplier;

public class ModLootTableProvider extends BlockLootSubProvider {
    public ModLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, registries);
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return Streams.stream(RegistryHelper.blockRegistry.iterator()).map(Supplier::get)
                .filter((block -> block != FireflyBlocks.CEDAR_WALL_SIGN.get())).toList();
    }

    @Override
    public void generate() {
        this.add(FireflyBlocks.CEDAR_DOOR.get(), this::createDoorTable);
        this.add(FireflyBlocks.CEDAR_LEAVES.get(), (block -> this.createLeavesDrops(block, FireflyBlocks.CEDAR_SAPLING.get(), 0.05F, 0.0625F, 0.083333336F, 0.1F)));
        this.add(FireflyBlocks.CEDAR_SLAB.get(), this::createSlabItemTable);
        this.add(FireflyBlocks.POTTED_CEDAR_SAPLING.get(), (block -> this.createPotFlowerItemTable(FireflyBlocks.CEDAR_SAPLING.get())));
        this.add(FireflyBlocks.SYMBOL_STONE_BRICK_SLAB.get(), this::createSlabItemTable);
        this.add(FireflyBlocks.XDI8AHO_PORTAL_BLOCK.get(), (block) -> BlockLootSubProvider.noDrop());
        this.add(FireflyBlocks.XDI8AHO_BACK_FIRE_BLOCK.get(), (block) -> BlockLootSubProvider.noDrop());
        ModDataGen.getDropSelfBlocks().forEach(this::dropSelf);
        LettersUtil.forEach((key, letter) -> this.dropSelf(SymbolStoneBlock.fromLetter(letter)));
    }
}
