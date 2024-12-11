package top.xdi8.mod.firefly8.item.tint;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;
import top.xdi8.mod.firefly8.item.FireflyItems;

/**
 * <p>Item tint map used in {@link
 * top.xdi8.mod.firefly8.item.tint.brewing.TintedPotionBrewingRecipe
 * potions}.</p>
 */
public final class ItemTinting {
    private static final BiMap<Item, Item> TINT_MAP = HashBiMap.create(fireflyTintMap());

    private static BiMap<Item, Item> fireflyTintMap() {
        return ImmutableBiMap.of(
                Items.POTION, FireflyItems.TINTED_POTION.get(),
                Items.LINGERING_POTION, FireflyItems.TINTED_LINGERING_POTION.get(),
                Items.SPLASH_POTION, FireflyItems.TINTED_SPLASH_POTION.get(),
                Items.HONEY_BOTTLE, FireflyItems.TINTED_HONEY_BOTTLE.get(),
                Items.DRAGON_BREATH, FireflyItems.TINTED_DRAGON_BREATH.get()
        );
    }

    /** @return the previous item bound to the un-tinted one, or null. */
    @Nullable
    @SuppressWarnings("unused")
    public static ItemLike register(Item from, Item to) {
        return TINT_MAP.put(from, to);
    }

    public static ItemStack tint(ItemStack stack) {
        Item item = TINT_MAP.get(stack.getItem());
        if (item == null) return stack.copy();
        return new ItemStack(Holder.direct(item), stack.getCount(), stack.getComponentsPatch());
    }

    public static ItemStack unTint(ItemStack stack) {
        Item item = TINT_MAP.inverse().get(stack.getItem());
        if (item == null) return stack.copy();
        return new ItemStack(Holder.direct(item), stack.getCount(), stack.getComponentsPatch());
    }

    public static boolean shouldTint(ItemStack stack) {
        return TINT_MAP.containsKey(stack.getItem());
    }
}
