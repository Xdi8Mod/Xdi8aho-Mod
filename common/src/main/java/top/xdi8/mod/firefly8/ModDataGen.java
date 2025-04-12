package top.xdi8.mod.firefly8;

import com.google.common.collect.ImmutableList;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import top.xdi8.mod.firefly8.block.symbol.SymbolStoneBlock;
import top.xdi8.mod.firefly8.core.letters.KeyedLetter;
import top.xdi8.mod.firefly8.item.FireflyItems;

import java.util.List;

public class ModDataGen {
    public static BlockFamily REDWOOD_FAMILY = new
            BlockFamily.Builder(FireflyBlocks.CEDAR_PLANKS.get())
            .button(FireflyBlocks.CEDAR_BUTTON.get())
            .fence(FireflyBlocks.CEDAR_FENCE.get())
            .fenceGate(FireflyBlocks.CEDAR_FENCE_GATE.get())
            .pressurePlate(FireflyBlocks.CEDAR_PRESSURE_PLATE.get())
            .sign(FireflyBlocks.CEDAR_SIGN.get(), FireflyBlocks.CEDAR_WALL_SIGN.get())
            .slab(FireflyBlocks.CEDAR_SLAB.get())
            .stairs(FireflyBlocks.CEDAR_STAIRS.get())
            .door(FireflyBlocks.CEDAR_DOOR.get())
            .trapdoor(FireflyBlocks.CEDAR_TRAPDOOR.get())
            .recipeGroupPrefix("wooden")
            .recipeUnlockedBy("has_planks")
            .getFamily();

    public static BlockFamily SYMBOL_STONE_FAMILY = new
            BlockFamily.Builder(FireflyBlocks.SYMBOL_STONE_BRICKS.get())
            .slab(FireflyBlocks.SYMBOL_STONE_BRICK_SLAB.get())
            .stairs(FireflyBlocks.SYMBOL_STONE_BRICK_STAIRS.get())
            .recipeGroupPrefix("symbol_stone_bricks")
            .getFamily();

    public static List<Block> getTrivialCubeBlocks(){
        ImmutableList.Builder<Block> builder = ImmutableList.builder();
        builder.add(FireflyBlocks.DARK_SYMBOL_STONE.get());
        builder.add(FireflyBlocks.DEEPSLATE_INDIUM_ORE_BLOCK.get());
        builder.add(FireflyBlocks.INDIUM_BLOCK.get());
        builder.add(FireflyBlocks.INDIUM_ORE_BLOCK.get());
        builder.add(FireflyBlocks.SYMBOL_STONE_NN.get());
        builder.add(FireflyBlocks.XDI8AHO_BACK_PORTAL_CORE_BLOCK.get());
        builder.add(FireflyBlocks.XDI8AHO_PORTAL_CORE_BLOCK.get());
        builder.add(SymbolStoneBlock.fromLetter(KeyedLetter.empty()));
        return builder.build();
    }

    public static List<Item> getFlatItems(){
        ImmutableList.Builder<Item> builder = ImmutableList.builder();
        builder.add(FireflyItems.INDIUM_AXE.get());
        builder.add(FireflyItems.INDIUM_CHISEL.get());
        builder.add(FireflyItems.INDIUM_HOE.get());
        builder.add(FireflyItems.INDIUM_INGOT.get());
        builder.add(FireflyItems.INDIUM_NUGGET.get());
        builder.add(FireflyItems.INDIUM_PICKAXE.get());
        builder.add(FireflyItems.INDIUM_SHOVEL.get());
        builder.add(FireflyItems.INDIUM_SWORD.get());
        builder.add(FireflyItems.TINTED_POTION.get());
        builder.add(FireflyItems.TINTED_SPLASH_POTION.get());
        builder.add(FireflyItems.TINTED_LINGERING_POTION.get());
        builder.add(FireflyItems.XDI8AHO_ICON.get());
        return builder.build();
    }

    public static List<Block> getDropSelfBlocks(){
        ImmutableList.Builder<Block> builder = ImmutableList.builder();
        builder.addAll(getTrivialCubeBlocks());
        builder.add(FireflyBlocks.CEDAR_BUTTON.get());
        builder.add(FireflyBlocks.CEDAR_FENCE.get());
        builder.add(FireflyBlocks.CEDAR_FENCE_GATE.get());
        builder.add(FireflyBlocks.CEDAR_LOG.get());
        builder.add(FireflyBlocks.CEDAR_PLANKS.get());
        builder.add(FireflyBlocks.CEDAR_PRESSURE_PLATE.get());
        builder.add(FireflyBlocks.CEDAR_SAPLING.get());
        builder.add(FireflyBlocks.CEDAR_SIGN.get());
        builder.add(FireflyBlocks.CEDAR_STAIRS.get());
        builder.add(FireflyBlocks.CEDAR_TRAPDOOR.get());
        builder.add(FireflyBlocks.CEDAR_WOOD.get());
        builder.add(FireflyBlocks.SYMBOL_STONE_BRICKS.get());
        builder.add(FireflyBlocks.SYMBOL_STONE_BRICK_STAIRS.get());
        builder.add(FireflyBlocks.STRIPPED_CEDAR_LOG.get());
        builder.add(FireflyBlocks.STRIPPED_CEDAR_WOOD.get());
        builder.add(FireflyBlocks.XDI8_TABLE.get());
        builder.add(FireflyBlocks.XDI8AHO_PORTAL_TOP_BLOCK.get());
        return builder.build();
    }
}
