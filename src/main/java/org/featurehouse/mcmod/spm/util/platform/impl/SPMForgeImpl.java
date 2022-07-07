package org.featurehouse.mcmod.spm.util.platform.impl;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.advancement.BalancedDietHelper;
import top.xdi8.mod.firefly8.item.tint.advancement.AdvancementLoadingEvent;

@Mod("sweet_potato")
public class SPMForgeImpl {
    private static final ResourceLocation BALANCED_DIET = new ResourceLocation("husbandry/balanced_diet");

    public SPMForgeImpl() {
        SPMMain.getInstance().onInitialize();
    }

    @Mod.EventBusSubscriber(modid = "sweet_potato")
    public static class Subscribers {
        @SubscribeEvent
        public static void loadingAdvancement(AdvancementLoadingEvent event) {
            if (BALANCED_DIET.equals(event.getId())) {
                BalancedDietHelper.setupCriteria(event.asContext());
            }
        }
    }
}
