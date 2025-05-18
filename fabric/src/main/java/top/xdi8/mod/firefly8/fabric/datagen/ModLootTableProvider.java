package top.xdi8.mod.firefly8.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.ModDataGen;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import top.xdi8.mod.firefly8.block.symbol.SymbolStoneBlock;
import top.xdi8.mod.firefly8.core.letters.LettersUtil;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        this.add(FireflyBlocks.CEDAR_DOOR.get(), this::createDoorTable);
        this.add(FireflyBlocks.CEDAR_LEAVES.get(), (block -> this.createLeavesDrops(block, FireflyBlocks.CEDAR_SAPLING.get(), 0.05F, 0.0625F, 0.083333336F, 0.1F)));
        this.add(FireflyBlocks.CEDAR_SLAB.get(), this::createSlabItemTable);
        this.add(FireflyBlocks.POTTED_CEDAR_SAPLING.get(), this::createPotFlowerItemTable);
        this.add(FireflyBlocks.SYMBOL_STONE_BRICK_SLAB.get(), this::createSlabItemTable);
        ModDataGen.getDropSelfBlocks().forEach(this::dropSelf);
        LettersUtil.forEach((key, letter) -> this.dropSelf(SymbolStoneBlock.fromLetter(letter)));
    }

    @Override
    public @NotNull String getName() {
        return "Firefly8 Loot Tables";
    }
}
