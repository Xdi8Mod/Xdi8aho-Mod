package org.featurehouse.mcmod.spm.advancement;

import com.google.common.collect.ImmutableList;
import net.minecraft.advancements.critereon.ConsumeItemTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.world.item.Item;
import org.featurehouse.mcmod.spm.SPMMain;
import top.xdi8.mod.firefly8.advancement.AdvancementLoadingContext;
import top.xdi8.mod.firefly8.util.ResourceLocationTool;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public final class BalancedDietHelper {
    private BalancedDietHelper() {}
    private static final ImmutableList<Supplier<Item>> ITEMS = ImmutableList.of(
            SPMMain.PURPLE_POTATO, SPMMain.RED_POTATO, SPMMain.WHITE_POTATO,
            SPMMain.BAKED_PURPLE_POTATO, SPMMain.BAKED_RED_POTATO, SPMMain.BAKED_WHITE_POTATO,
            SPMMain.ENCHANTED_PURPLE_POTATO, SPMMain.ENCHANTED_RED_POTATO, SPMMain.ENCHANTED_WHITE_POTATO
    );

    public static void setupCriteria(AdvancementLoadingContext ctx) {
        List<Supplier<Item>> itemList = ITEMS;
        int itemListSize = itemList.size();

        String[][] requirementsOld = ctx.getRequirements();
        String[][] requirementsNew = new String[requirementsOld.length + itemListSize][];
        System.arraycopy(requirementsOld, 0, requirementsNew, itemListSize, requirementsOld.length);

        for (int i = 0; i < itemListSize; ++i) {
            String reqId = "sweet_potato:balanced_diet__food/" + i;
            ctx.addCriterion(ResourceLocationTool.create(reqId),
                    new ConsumeItemTrigger.TriggerInstance(Optional.empty(),
                            Optional.of(ItemPredicate.Builder.item().of(itemList.get(i).get()).build())));
            requirementsNew[i] = new String[] {reqId};
        }
        ctx.setRequirements(requirementsNew);
    }
}
