package top.xdi8.mod.firefly8.item.tint.advancement;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.Event;
import org.apache.commons.lang3.mutable.Mutable;

public class AdvancementLoadingEvent extends Event {
    private final ResourceLocation id;
    private final Advancement.Builder task;
    private final Mutable<String[][]> requirements;

    public AdvancementLoadingEvent(ResourceLocation id,
                                   Advancement.Builder task,
                                   Mutable<String[][]> requirements) {
        this.id = id;
        this.task = task;
        this.requirements = requirements;
    }

    public ResourceLocation getId() {
        return id;
    }

    public Advancement.Builder getTask() {
        return task;
    }

    public String[][] getRequirements() {
        return requirements.getValue();
    }

    public void setRequirements(String[][] req) {
        requirements.setValue(req);
    }

    public void addCriterion(ResourceLocation id,
                             CriterionTriggerInstance trigger) {
        getTask().addCriterion(id.toString(), trigger);
    }
}
