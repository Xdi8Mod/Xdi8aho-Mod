package top.xdi8.mod.firefly8.item.potion.vanilla;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.common.brewing.VanillaBrewingRecipe;
import net.minecraftforge.registries.RegistryObject;
import top.xdi8.mod.firefly8.item.FireflyItems;
import top.xdi8.mod.firefly8.item.potion.ItemTinting;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.Set;

/**
 * <p>Register tinted potion for corresponding vanilla
 * ones.</p>
 * TODO: Support other types
 * @see VanillaBrewingRecipe
 */
public class TintedPotionBrewing extends VanillaBrewingRecipe
        implements IBrewingRecipe {
    public static void register() {
        BrewingRecipeRegistry.addRecipe(new TintedPotionBrewing());
    }

    private static final Set<RegistryObject<Item>> INGREDIENTS
            = Set.of(FireflyItems.TINTED_POTION, FireflyItems.TINTED_LINGERING_POTION,
                FireflyItems.TINTED_SPLASH_POTION);

    @Override
    public boolean isInput(@NotNull ItemStack input) {
        return INGREDIENTS.stream().anyMatch(o -> input.is(o.get()));
    }

    @Override
    public boolean isIngredient(@NotNull ItemStack ingredient) {
        return super.isIngredient(ingredient);
    }

    @Override
    public @NotNull ItemStack getOutput(@NotNull ItemStack input, @NotNull ItemStack ingredient) {
        ItemStack sup = super.getOutput(input, ingredient);
        LOGGER.debug("Brewing output[sup]: {}, input: {}, ing: {}", sup, input, ingredient);
        return ItemTinting.tint(sup);   // TODO: is this necessary?
    }

    private static final Logger LOGGER = LogUtils.getLogger();
}
