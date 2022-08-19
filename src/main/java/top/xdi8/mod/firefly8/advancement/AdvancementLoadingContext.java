package top.xdi8.mod.firefly8.advancement;

import dev.architectury.annotations.ForgeEvent;
import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.mutable.Mutable;

import java.util.function.Consumer;

@ForgeEvent
public record AdvancementLoadingContext(ResourceLocation id,
                                        Advancement.Builder task) {

    public static final Event<Consumer<AdvancementLoadingContext>> EVENT = EventFactory.createConsumerLoop(AdvancementLoadingContext.class);

    public String[][] getRequirements() {
        return task().requirements;
    }

    public void setRequirements(String[][] req) {
        task().requirements = req;
    }

    public void addCriterion(ResourceLocation id,
                             CriterionTriggerInstance trigger) {
        task().addCriterion(id.toString(), trigger);
    }
}
