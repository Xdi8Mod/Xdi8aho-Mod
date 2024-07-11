package top.xdi8.mod.firefly8.item.tint.brewing.forge;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.ItemStack;
//import net.neoforged.common.brewing.BrewingRecipeRegistry;
//import net.neoforged.common.brewing.IBrewingRecipe;
//import net.neoforged.common.brewing.VanillaBrewingRecipe;
import net.neoforged.neoforge.common.brewing.BrewingRecipe;
import net.neoforged.neoforge.common.brewing.IBrewingRecipe;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import top.xdi8.mod.firefly8.item.tint.ItemTinting;
import top.xdi8.mod.firefly8.item.tint.brewing.TintedPotionBrewingRecipe;

/**
 * <p>Register tinted potion for corresponding vanilla
 * ones.</p>
 * TODO: Support other types
 * @see BrewingRecipe
 */
public class TintedPotionBrewingRecipeImpl extends BrewingRecipe {
    public static void register() {
        BrewingRecipeRegistry.addRecipe(new TintedPotionBrewingRecipeImpl());
    }

    @Override
    public boolean isInput(@NotNull ItemStack input) {
        return TintedPotionBrewingRecipe.isInput(input);
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
