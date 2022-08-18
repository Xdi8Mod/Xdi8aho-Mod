package org.featurehouse.mcmod.spm.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ItemStacks {
    private ItemStacks() {}

    public static @NotNull ItemStack of(@NotNull ItemLike item, int count, @Nullable CompoundTag tag) {
        ItemStack stack = new ItemStack(item, count);
        stack.setTag(tag);
        return stack;
    }

    public static Ingredient expandIngredient(Ingredient old, Item[] added) {
        JsonArray a = serializeIngredientToArray(old);
        a.addAll(serializeIngredientToArray(Ingredient.of(added)));
        if (a.isEmpty()) return Ingredient.EMPTY;
        return Ingredient.fromJson(a);
    }

    private static JsonArray serializeIngredientToArray(Ingredient ingredient) {
        final JsonElement e = ingredient.toJson();
        if (e.isJsonArray()) return e.getAsJsonArray();
        else {
            JsonArray arr = new JsonArray();
            arr.add(e);
            return arr;
        }
    }
}
