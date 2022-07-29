package org.featurehouse.mcmod.spm.platform.api.advacement;

import net.minecraft.advancements.CriterionTrigger;

public interface CriterionRegistry {
    static <T extends CriterionTrigger<?>> T register(T criterion) {
        return net.minecraft.advancements.CriteriaTriggers.register(criterion);
    }
}
