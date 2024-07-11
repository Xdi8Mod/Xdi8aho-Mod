package top.xdi8.mod.firefly8.item.tint.brewing;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.xdi8.mod.firefly8.item.FireflyItems;

import java.util.Set;
import java.util.function.Supplier;

public class TintedPotionBrewingRecipe {
    // TODO: rewrite
    public static final Set<Supplier<Item>> INGREDIENTS
            = Set.of(FireflyItems.TINTED_POTION, FireflyItems.TINTED_LINGERING_POTION,
                FireflyItems.TINTED_SPLASH_POTION, FireflyItems.TINTED_GLASS_BOTTLE);

    @ExpectPlatform
    public static void register() {}

    public static boolean isInput(ItemStack input) {
        return TintedPotionBrewingRecipe.INGREDIENTS.stream().anyMatch(o -> input.is(o.get()));
    }
}
