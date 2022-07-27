package top.xdi8.mod.firefly8.item.tint.mixin;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.conditions.ICondition;
import org.apache.commons.lang3.mutable.Mutable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.xdi8.mod.firefly8.advancement.AdvancementLoadingEvent;

@Mixin(Advancement.Builder.class)
abstract class AdvancementsMixin {
    @Inject(at = @At("RETURN"),
            method = "fromJson(Lcom/google/gson/JsonObject;Lnet/minecraft/advancements/critereon/DeserializationContext;Lnet/minecraftforge/common/crafting/conditions/ICondition$IContext;)Lnet/minecraft/advancements/Advancement$Builder;",
            remap = false   // Forge-added method
    )
    private static void hackJson(JsonObject pJson, DeserializationContext pContext, ICondition.IContext context, CallbackInfoReturnable<Advancement.Builder> cir) {
        final Advancement.Builder builder = cir.getReturnValue();
        AdvancementLoadingEvent event = new AdvancementLoadingEvent(pContext.getAdvancementId(),
                builder,
                new Mutable<>() {
                    @Override
                    public String[][] getValue() {
                        return builder.requirements;
                    }

                    @Override
                    public void setValue(String[][] value) {
                        builder.requirements(value);
                    }
                });
        MinecraftForge.EVENT_BUS.post(event);
    }
}
