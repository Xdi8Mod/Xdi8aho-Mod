package org.featurehouse.mcfgmod.firefly8.item.potion;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.featurehouse.mcfgmod.firefly8.item.*;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

import static org.featurehouse.mcfgmod.firefly8.item.FireflyItems.*;

/**
 * <p>Item tint map used in {@link
 * org.featurehouse.mcfgmod.firefly8.item.potion.vanilla.TintedPotionBrewing
 * potions}.</p>
 */
public final class ItemTinting {
    private static final Map<ItemLike, ItemLike> TINT_MAP = new HashMap<>(fireflyTintMap());

    private static Map<ItemLike, ItemLike> fireflyTintMap() {
        return ImmutableMap.of(
                Items.POTION, TINTED_POTION::get,
                Items.LINGERING_POTION, TINTED_LINGERING_POTION::get,
                Items.SPLASH_POTION, TINTED_SPLASH_POTION::get
        );
    }

    /** @return the previous item bound to the un-tinted one, or null. */
    @Nullable
    @SuppressWarnings("unused")
    public static ItemLike register(ItemLike from, ItemLike to) {
        return TINT_MAP.put(from, to);
    }

    public static ItemStack tint(ItemStack stack) {
        ItemLike itemLike = TINT_MAP.get(stack.getItem());
        if (itemLike == null) return stack.copy();
        return new ItemStack(itemLike, stack.getCount(), stack.getTag());
    }

    public static boolean shouldTint(ItemStack stack) {
        return TINT_MAP.containsKey(stack.getItem());
    }
}
