package top.xdi8.mod.firefly8.item.tint.advanceent;

import com.google.common.collect.ImmutableMap;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import top.xdi8.mod.firefly8.advancement.AdvancementLoadingContext;
import top.xdi8.mod.firefly8.item.FireflyItemTags;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public class VanillaAdvancements {
    private static final ResourceLocation SAFELY_HARVEST_HONEY = new ResourceLocation("husbandry/safely_harvest_honey");
    private static final ResourceLocation DRAGON_BREATH = new ResourceLocation("end/dragon_breath");
    private static final ResourceLocation BALANCED_DIET = new ResourceLocation("husbandry/balanced_diet");
    private static final TagKey<Block> BEEHIVES_TAG = BlockTags.BEEHIVES;

    private static final Map<ResourceLocation, UUID> TO_UUID = ImmutableMap.of(
            SAFELY_HARVEST_HONEY, UUID.fromString("b0ec73e8-c187-4f25-a83c-cc42263282b7"),
            DRAGON_BREATH, UUID.fromString("fe4d3782-e9b4-431a-a4bc-5b508852ef3c"),
            BALANCED_DIET, UUID.fromString("a1b41569-63e3-4895-89fb-e3a76d58d414"));
    private static final Map<ResourceLocation, Supplier<CriterionTriggerInstance>> TO_TRIGGER = ImmutableMap.of(
            SAFELY_HARVEST_HONEY, () -> new ItemUsedOnBlockTrigger.TriggerInstance(
                    EntityPredicate.Composite.ANY,
                    LocationPredicate.Builder.location()
                            .setSmokey(true)
                            .setBlock(BlockPredicate.Builder.block().of(BEEHIVES_TAG).build())
                            .build(),
                    ItemPredicate.Builder.item()
                            .of(FireflyItemTags.TINTED_HONEY_BOTTLES)
                            .build()
            ),
            DRAGON_BREATH, () -> new InventoryChangeTrigger.TriggerInstance(
                    EntityPredicate.Composite.ANY,
                    MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY,
                    new ItemPredicate[]{ItemPredicate.Builder.item()
                            .of(FireflyItemTags.TINTED_DRAGON_BREATH)
                            .build()}
            )
    );

    //@SubscribeEvent
    public static void patchTintedItem(AdvancementLoadingContext context) {
        final ResourceLocation id = context.id;
        if (BALANCED_DIET.equals(id)) {
            balancedDiet(context);
            return;
        }
        if (!TO_UUID.containsKey(id)) return;
        String[][] req = context.getRequirements();
        ResourceLocation reqId = new ResourceLocation("firefly8",
                TO_UUID.get(id).toString());
        context.addCriterion(reqId, TO_TRIGGER.get(id).get());
        String[] instance = req[0], newInstance = Arrays.copyOf(instance, instance.length+1);
        newInstance[instance.length] = reqId.toString();
        req[0] = newInstance;
    }

    /* BalancedDietHelper start */

    private static void balancedDiet(AdvancementLoadingContext context) {
        String[][] requirements = context.getRequirements();

        // 0: honey bottles
        for (int i = 0; i < requirements.length; i++) {
            String[] as = requirements[i];
            if (Arrays.asList(as).contains("honey_bottle")) {
                context.addCriterion(new ResourceLocation("firefly8:05f921ae-5f96-410d-bcce-bf20d57e5d1a"),
                        consumeItemTrigger(FireflyItemTags.TINTED_HONEY_BOTTLES));
                String[] nas = Arrays.copyOf(as, as.length + 1);
                nas[as.length] = "firefly8:05f921ae-5f96-410d-bcce-bf20d57e5d1a";
                requirements[i] = nas;
            }
        }
    }

    private static ConsumeItemTrigger.TriggerInstance consumeItemTrigger(TagKey<Item> tag) {
        return new ConsumeItemTrigger.TriggerInstance(EntityPredicate.Composite.ANY,
                ItemPredicate.Builder.item().of(tag).build());
    }
}
