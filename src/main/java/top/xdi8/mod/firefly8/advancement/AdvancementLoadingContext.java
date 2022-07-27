package top.xdi8.mod.firefly8.advancement;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.mutable.Mutable;

public record AdvancementLoadingContext(ResourceLocation id,
                                        Advancement.Builder task,
                                        Mutable<String[][]> requirements) {
    public String[][] getRequirements() {
        return requirements().getValue();
    }

    public void setRequirements(String[][] req) {
        requirements().setValue(req);
    }

    public void addCriterion(ResourceLocation id,
                             CriterionTriggerInstance trigger) {
        task().addCriterion(id.toString(), trigger);
    }
}
