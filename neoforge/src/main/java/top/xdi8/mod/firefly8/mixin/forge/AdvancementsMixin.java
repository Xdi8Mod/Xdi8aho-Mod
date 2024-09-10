package top.xdi8.mod.firefly8.mixin.forge;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.neoforged.common.crafting.conditions.ICondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.xdi8.mod.firefly8.advancement.AdvancementLoadingContext;

@Mixin(Advancement.Builder.class)
abstract class AdvancementsMixin {
    @Inject(at = @At("RETURN"),
            method = "fromJson(Lcom/google/gson/JsonObject;Lnet/minecraft/advancements/critereon/DeserializationContext;Lnet/minecraftforge/common/crafting/conditions/ICondition$IContext;)Lnet/minecraft/advancements/Advancement$Builder;",
            remap = false   // Forge-added method
    )
    private static void hackJson(JsonObject pJson, DeserializationContext pContext, ICondition.IContext context, CallbackInfoReturnable<Advancement.Builder> cir) {
        final Advancement.Builder builder = cir.getReturnValue();
        AdvancementLoadingContext.EVENT.invoker().accept(new AdvancementLoadingContext(pContext.getAdvancementId(), builder));
    }
}
