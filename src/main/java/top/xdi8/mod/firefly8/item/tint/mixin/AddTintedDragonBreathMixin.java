package top.xdi8.mod.firefly8.item.tint.mixin;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionBrewing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.xdi8.mod.firefly8.item.FireflyItemTags;

@Mixin(PotionBrewing.class)
abstract class AddTintedDragonBreathMixin {
    @Inject(at = @At("HEAD"), method = "isIngredient",
    cancellable = true)
    private static void hackIngredientPredicate(ItemStack pInput, CallbackInfoReturnable<Boolean> cir) {
        if (pInput.is(FireflyItemTags.TINTED_DRAGON_BREATH)) cir.setReturnValue(true);
    }
}
