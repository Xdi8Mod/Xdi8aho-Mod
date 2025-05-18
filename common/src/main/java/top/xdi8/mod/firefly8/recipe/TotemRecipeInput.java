package top.xdi8.mod.firefly8.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.core.letters.KeyedLetter;
import top.xdi8.mod.firefly8.item.symbol.SymbolStoneBlockItem;

import java.util.List;

public class TotemRecipeInput implements RecipeInput {
    public final ItemStack totem;
    public final List<KeyedLetter> letters;
    private final int inputSize;

    public TotemRecipeInput(ItemStack totem, List<KeyedLetter> letters){
        this.totem = totem;
        this.letters = letters;
        this.inputSize = letters.size();
    }

    @Override
    public @NotNull ItemStack getItem(int index) {
        if (index == 0) return totem;
        else if(index <= size()) return new ItemStack(SymbolStoneBlockItem.fromLetter(letters.get(index - 1)));
        else throw new IllegalArgumentException("Recipe does not contain slot " + index);
    }

    @Override
    public int size() {
        return this.inputSize;
    }

    @Override
    public boolean isEmpty() {
        return RecipeInput.super.isEmpty();
    }
}
