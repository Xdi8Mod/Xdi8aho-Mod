package top.xdi8.mod.firefly8;

import com.google.common.collect.ImmutableList;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.level.block.Block;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import top.xdi8.mod.firefly8.block.symbol.SymbolStoneBlock;
import top.xdi8.mod.firefly8.core.letters.KeyedLetter;

import java.util.List;

public class ModDataGen {
    public static BlockFamily FAMILY = new BlockFamily.Builder(FireflyBlocks.CEDAR_PLANKS.get())
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

    public static List<Block> getTrivialCubeBlocks(){
        ImmutableList.Builder<Block> builder = ImmutableList.builder();
        builder.add(FireflyBlocks.DARK_SYMBOL_STONE.get());
        builder.add(FireflyBlocks.DEEPSLATE_INDIUM_ORE_BLOCK.get());
        builder.add(FireflyBlocks.INDIUM_BLOCK.get());
        builder.add(FireflyBlocks.INDIUM_ORE_BLOCK.get());
        builder.add(SymbolStoneBlock.fromLetter(KeyedLetter.empty()));
        builder.add(FireflyBlocks.SYMBOL_STONE_BRICKS.get());
        builder.add(FireflyBlocks.SYMBOL_STONE_NN.get());
        return builder.build();
    }
}
