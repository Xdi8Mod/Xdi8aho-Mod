package top.xdi8.mod.firefly8.item.tint.mixin;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.xdi8.mod.firefly8.item.FireflyItems;

@Mixin(PotionBrewing.class)
abstract class AddTintedDragonBreathMixin {
    @Shadow
    private static void addContainerRecipe(Item pFrom, Item pIngredient, Item pTo) {}

    @Inject(at = @At("RETURN"), method = "bootStrap()V")
    private static void add(CallbackInfo ci) {
        addContainerRecipe(Items.SPLASH_POTION,
                FireflyItems.TINTED_DRAGON_BREATH.get(),
                Items.LINGERING_POTION);
    }
}
