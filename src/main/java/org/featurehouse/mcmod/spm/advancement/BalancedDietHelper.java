package org.featurehouse.mcmod.spm.advancement;

import com.google.common.collect.ImmutableList;
import net.minecraft.advancements.critereon.ConsumeItemTrigger;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.featurehouse.mcmod.spm.SPMMain;
import top.xdi8.mod.firefly8.item.tint.advancement.AdvancementLoadingContext;

import java.util.List;

public final class BalancedDietHelper {
    private BalancedDietHelper() {}
    private static final ImmutableList<Item> ITEMS = ImmutableList.of(
            SPMMain.PURPLE_POTATO, SPMMain.RED_POTATO, SPMMain.WHITE_POTATO,
            SPMMain.BAKED_PURPLE_POTATO, SPMMain.BAKED_RED_POTATO, SPMMain.BAKED_WHITE_POTATO,
            SPMMain.ENCHANTED_PURPLE_POTATO, SPMMain.ENCHANTED_RED_POTATO, SPMMain.ENCHANTED_WHITE_POTATO
    );

    public static void setupCriteria(AdvancementLoadingContext ctx) {
        List<Item> itemList = ITEMS;
        int itemListSize = itemList.size();

        String[][] requirementsOld = ctx.getRequirements();
        String[][] requirementsNew = new String[requirementsOld.length + itemListSize][];
        System.arraycopy(requirementsOld, 0, requirementsNew, itemListSize, requirementsOld.length);

        for (int i = 0; i < itemListSize; ++i) {
            String reqId = "sweet_potato:balanced_diet__food$" + itemList.get(i);
            ctx.addCriterion(new ResourceLocation(reqId),
                    new ConsumeItemTrigger.TriggerInstance(EntityPredicate.Composite.ANY,
                            ItemPredicate.Builder.item().of(itemList.get(i)).build()));
            requirementsNew[i] = new String[] {reqId};
        }
        ctx.setRequirements(requirementsNew);
    }
}
