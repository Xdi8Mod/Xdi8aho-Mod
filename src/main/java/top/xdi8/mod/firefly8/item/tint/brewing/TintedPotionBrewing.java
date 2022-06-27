package top.xdi8.mod.firefly8.item.tint.brewing;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.common.brewing.VanillaBrewingRecipe;
import net.minecraftforge.registries.RegistryObject;
import top.xdi8.mod.firefly8.item.FireflyItems;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import top.xdi8.mod.firefly8.item.tint.ItemTinting;

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
        var unTint = ItemTinting.unTint(input);
        ItemStack sup = super.getOutput(unTint, ingredient);
        LOGGER.debug("Brewing output[sup]: {}, input: {}, un: {} ing: {}", sup, input, unTint, ingredient);
        return ItemTinting.tint(sup);
    }

    private static final Logger LOGGER = LogUtils.getLogger();
}
