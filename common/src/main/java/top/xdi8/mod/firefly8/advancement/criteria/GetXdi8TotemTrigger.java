package top.xdi8.mod.firefly8.advancement.criteria;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.core.totem.TotemAbilityPredicate;

public class GetXdi8TotemTrigger extends SimpleCriterionTrigger<GetXdi8TotemTrigger.TriggerInstance> {
    @Override
    protected @NotNull TriggerInstance createInstance(@NotNull JsonObject pJson, @NotNull EntityPredicate.Composite pPlayer, @NotNull DeserializationContext pContext) {
        return new TriggerInstance(pPlayer, TotemAbilityPredicate.fromJson(pJson.get("totem")));
    }

    public void trigger(ServerPlayer player, ItemStack stack) {
        this.trigger(player, instance -> instance.matches(stack));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final TotemAbilityPredicate totem;

        public TriggerInstance(EntityPredicate.Composite pPlayer, TotemAbilityPredicate totem) {
            super(THE_ID, pPlayer);
            this.totem = totem;
        }

        public boolean matches(@NotNull ItemStack stack) {
            return totem.matches(stack);
        }

        @Override
        public @NotNull JsonObject serializeToJson(@NotNull SerializationContext pConditions) {
            final JsonObject obj = super.serializeToJson(pConditions);
            obj.add("totem", totem.toJson());
            return obj;
        }
    }

    static final ResourceLocation THE_ID = new ResourceLocation("firefly8", "get_xdi8_totem");

    @Override
    public @NotNull ResourceLocation getId() {
        return THE_ID;
    }
}
