package top.xdi8.mod.firefly8.advancement;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.mutable.Mutable;
import org.featurehouse.mcmod.spm.platform.api.event.BaseEvent;

public class AdvancementLoadingEvent extends BaseEvent {
    private final AdvancementLoadingContext ctx;

    public AdvancementLoadingEvent(ResourceLocation id,
                                   Advancement.Builder task,
                                   Mutable<String[][]> requirements) {
        this.ctx = new AdvancementLoadingContext(id, task, requirements);
    }

    public ResourceLocation getId() {
        return ctx.id();
    }

    public Advancement.Builder getTask() {
        return ctx.task();
    }

    public String[][] getRequirements() {
        return ctx.getRequirements();
    }

    public void setRequirements(String[][] req) {
        ctx.setRequirements(req);
    }

    public void addCriterion(ResourceLocation id,
                             CriterionTriggerInstance trigger) {
        ctx.addCriterion(id, trigger);
    }

    public AdvancementLoadingContext asContext() {
        return ctx;
    }
}
