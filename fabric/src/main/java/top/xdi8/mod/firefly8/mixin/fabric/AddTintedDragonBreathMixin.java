package top.xdi8.mod.firefly8.mixin.fabric;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.xdi8.mod.firefly8.item.FireflyItemTags;

import java.util.List;

@Mixin(PotionBrewing.class)
abstract class AddTintedDragonBreathMixin {
    @Accessor("CONTAINER_MIXES")
    private static List<PotionBrewing.Mix<Item>> getContainerMixes() { throw new AssertionError("MIXIN"); }

    @Inject(at = @At("RETURN"), method = "bootStrap()V")
    private static void hackBootstrap(CallbackInfo ci) {
        getContainerMixes().add(new PotionBrewing.Mix<>(
                Items.SPLASH_POTION,
                Ingredient.of(FireflyItemTags.TINTED_DRAGON_BREATH.stream()),
                Items.LINGERING_POTION
        ));
    }
}
