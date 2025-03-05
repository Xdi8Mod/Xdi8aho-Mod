package top.xdi8.mod.firefly8.mixin;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.critereon.ConsumeItemTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.advancements.packs.VanillaHusbandryAdvancements;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.xdi8.mod.firefly8.item.FireflyItemTags;

@Mixin(VanillaHusbandryAdvancements.class)
public abstract class VanillaHusbandryAdvancementMixin {
    @Inject(method = "addFood(Lnet/minecraft/advancements/Advancement$Builder;Lnet/minecraft/core/HolderGetter;)Lnet/minecraft/advancements/Advancement$Builder;",
            at = @At("RETURN"), cancellable = true)
    private static void addFood(Advancement.Builder builder, HolderGetter<Item> holderGetter, CallbackInfoReturnable<Advancement.Builder> cir) {
        builder.addCriterion("firefly8:honey_bottle", ConsumeItemTrigger.TriggerInstance.usedItem(
                ItemPredicate.Builder.item().of(holderGetter, FireflyItemTags.TINTED_HONEY_BOTTLES.tagKey())));
        cir.setReturnValue(builder);
    }

    // TODO: Add tinted honey bottles (tag) to the vanilla advancement
}

