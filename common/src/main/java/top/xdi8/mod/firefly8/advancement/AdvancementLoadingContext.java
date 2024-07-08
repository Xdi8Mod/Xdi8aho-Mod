package top.xdi8.mod.firefly8.advancement;

import dev.architectury.annotations.ForgeEvent;
import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

@ForgeEvent
public class AdvancementLoadingContext {
    // 2023-9-27: Rewrote a record class to a normal class, to avoid this exception:
    // Type 'java/lang/Record' is not assignable to 'top/xdi8/mod/firefly8/advancement/AdvancementLoadingContext'
    public final ResourceLocation id;
    public final Advancement.Builder task;

    public AdvancementLoadingContext(ResourceLocation id, Advancement.Builder task) {
        this.id = id;
        this.task = task;
    }

    public static final Event<Consumer<AdvancementLoadingContext>> EVENT = EventFactory.createConsumerLoop(AdvancementLoadingContext.class);

    public String[][] getRequirements() {
        return this.task.requirements;
    }

    public void setRequirements(String[][] req) {
        this.task.requirements = req;
    }

    public void addCriterion(ResourceLocation id,
                             CriterionTriggerInstance trigger) {
        this.task.addCriterion(id.toString(), trigger);
    }
}
