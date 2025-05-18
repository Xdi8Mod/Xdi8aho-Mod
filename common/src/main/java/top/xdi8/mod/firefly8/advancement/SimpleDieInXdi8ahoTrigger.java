package top.xdi8.mod.firefly8.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class SimpleDieInXdi8ahoTrigger extends SimpleCriterionTrigger<SimpleDieInXdi8ahoTrigger.TriggerInstance> {
    @Override
    public @NotNull Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        LootContext context = EntityPredicate.createContext(player, player);
        this.trigger(player, (triggerInstance) -> triggerInstance.matches(context));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<SimpleDieInXdi8ahoTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
                (instance) -> instance.group(EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player)).apply(instance, TriggerInstance::new)
        );

        public boolean matches(LootContext context){
            return player.isEmpty() || player.get().matches(context);
        }
    }
}
