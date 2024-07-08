package org.featurehouse.mcmod.spm.util.registries;

import it.unimi.dsi.fastutil.objects.Object2DoubleLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.featurehouse.mcmod.spm.util.tag.TagLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.OptionalDouble;

/** @apiNote in forge, must be fired in an event, like FMLCommonSetupEvent */
public final class GrindingUtils {
    public static IngredientDataMap ingredientDataMap() {
        return IngredientDataMap.INSTANCE;
    }

    private GrindingUtils() {}

    public static void registerGrindableItem(double ingredientDataAdded, @NotNull ItemLike item) {
        Objects.requireNonNull(item, "item");
        ingredientDataMap().put(item.asItem(), ingredientDataAdded);
    }

    public static void registerGrindableTag(double ingredientDataAdded, @NotNull TagKey<Item> tagContainer) {
        Objects.requireNonNull(tagContainer, "tagContainer");
        ingredientDataMap().put(tagContainer, ingredientDataAdded);
    }

    public static boolean grindable(@Nullable ItemStack itemStack) {
        if (itemStack == null)
            return false;
        return grindable(itemStack.getItem());
    }

    public static boolean grindable(@Nullable ItemLike item) {
        if (item == null)
            return false;
        return ingredientDataMap().containsItem(item.asItem());
    }

    public record IngredientDataMap(Object2DoubleMap<TagLike<Item>> map) {
        private static final IngredientDataMap INSTANCE = new IngredientDataMap();

        public IngredientDataMap() {
            this(new Object2DoubleLinkedOpenHashMap<>());
        }

        public double put(Item key, double value) {
            return map.put(TagLike.asItem(key), value);
        }

        public double put(TagKey<Item> key, double value) {
            return map.put(TagLike.asTag(key), value);
        }

        private OptionalDouble get0(Holder<Item> key) {
            for (var entry : map.object2DoubleEntrySet()) {
                if (entry.getKey().contains(key))
                    return OptionalDouble.of(entry.getDoubleValue());
            } return OptionalDouble.empty();
        }

        public double getDouble(Item key) {
            return get0(key.arch$holder()).orElse(map.defaultReturnValue());
        }

        public boolean containsItem(Item key) {
            return containsItem(key.arch$holder());
        }

        public boolean containsItem(Holder<Item> itemHolder) {
            return get0(itemHolder).isPresent();
        }

        @Override
        public String toString() {
            return map.toString();
        }
    }
}
