package top.xdi8.mod.firefly8.mixin.fabric;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import top.xdi8.mod.firefly8.item.tint.brewing.fabric.TintedPotionBrewingRecipeImpl;

@Mixin(BrewingStandBlockEntity.class)
// TODO: for mod compatibility, we can enjoy some asm
public class BrewingStandBlockEntityMixin {
    @Redirect(method = "isBrewable",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/alchemy/PotionBrewing;hasMix(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Z"
            ))
    private static boolean redirectHasMix(ItemStack input, ItemStack ingredient) {
        return TintedPotionBrewingRecipeImpl.preHasMix(input, ingredient) || PotionBrewing.hasMix(input, ingredient);
    }

    @Redirect(method = "doBrew",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/alchemy/PotionBrewing;mix(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/item/ItemStack;"
            ))
    private static ItemStack redirectMix(ItemStack itemStack, ItemStack itemStack2) {
        return TintedPotionBrewingRecipeImpl.redirectMix(itemStack, itemStack2);
    }
}
