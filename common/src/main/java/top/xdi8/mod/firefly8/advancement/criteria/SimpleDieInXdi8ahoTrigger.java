package top.xdi8.mod.firefly8.advancement.criteria;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.util.ResourceLocationTool;

public class SimpleDieInXdi8ahoTrigger extends SimpleCriterionTrigger<SimpleDieInXdi8ahoTrigger.TriggerInstance> {
    @Override
    protected @NotNull TriggerInstance createInstance(@NotNull JsonObject pJson, @NotNull EntityPredicate.Composite pPlayer, @NotNull DeserializationContext pContext) {
        return new TriggerInstance(pPlayer);
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, triggerInstance -> true);
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return THE_ID;
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        public TriggerInstance(EntityPredicate.Composite pPlayer) {
            super(THE_ID, pPlayer);
        }
    }

    private static final ResourceLocation THE_ID = ResourceLocationTool.create("firefly8", "die_in_xdi8aho");
}
