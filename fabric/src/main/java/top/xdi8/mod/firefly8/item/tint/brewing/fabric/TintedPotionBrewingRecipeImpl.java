package top.xdi8.mod.firefly8.item.tint.brewing.fabric;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionBrewing;
import top.xdi8.mod.firefly8.item.tint.ItemTinting;
import top.xdi8.mod.firefly8.item.tint.brewing.TintedPotionBrewingRecipe;

public class TintedPotionBrewingRecipeImpl {
    public static void register() {}    // Fabric: NOOP

    public static boolean preHasMix(ItemStack input, ItemStack ingredient) {
        return TintedPotionBrewingRecipe.isInput(input) &&
                PotionBrewing.hasMix(ItemTinting.unTint(input), ingredient);
    }

    public static ItemStack redirectMix(ItemStack input, ItemStack ingredient) {
        if (!TintedPotionBrewingRecipe.isInput(input)) return PotionBrewing.mix(input, ingredient);
        final ItemStack mixed = PotionBrewing.mix(ItemTinting.unTint(input), ingredient);
        return ItemTinting.tint(mixed);
    }

    private static PotionBrewing getPotionBrewing(){

    }
}
