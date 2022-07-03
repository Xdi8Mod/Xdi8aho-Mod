package top.xdi8.mod.firefly8.item.tint.advancement;

import com.google.common.collect.ImmutableMap;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.xdi8.mod.firefly8.item.FireflyItemTags;

import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = "firefly8")
public class VanillaAdvancements {
    private static final ResourceLocation SAFELY_HARVEST_MONEY = new ResourceLocation("husbandry/safely_harvest_honey");
    private static final ResourceLocation DRAGON_BREATH = new ResourceLocation("end/dragon_breath");
    private static final ResourceLocation BALANCED_DIET = new ResourceLocation("husbandry/balanced_diet");
    private static final TagKey<Block> BEEHIVES_TAG = BlockTags.create(new ResourceLocation("beehives"));

    private static final Map<ResourceLocation, UUID> TO_UUID = ImmutableMap.of(
            SAFELY_HARVEST_MONEY, UUID.fromString("b0ec73e8-c187-4f25-a83c-cc42263282b7"),
            DRAGON_BREATH, UUID.fromString("fe4d3782-e9b4-431a-a4bc-5b508852ef3c"),
            BALANCED_DIET, UUID.fromString("a1b41569-63e3-4895-89fb-e3a76d58d414"));
    private static final Map<ResourceLocation, Supplier<CriterionTriggerInstance>> TO_TRIGGER = ImmutableMap.of(
            SAFELY_HARVEST_MONEY, () -> new ItemUsedOnBlockTrigger.TriggerInstance(
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
            ),
            BALANCED_DIET, () -> new ConsumeItemTrigger.TriggerInstance(
                    EntityPredicate.Composite.ANY,
                    ItemPredicate.Builder.item()
                            .of(FireflyItemTags.BALANCED_DIET)
                            .build()
            )
    );

    @SubscribeEvent
    public static void patchTintedItem(AdvancementLoadingEvent event) {
        final ResourceLocation id = event.getId();
        if (!TO_UUID.containsKey(id)) return;
        String[][] reqOld = event.getRequirements(),
                   reqNew = new String[reqOld.length + 1][];
        System.arraycopy(reqOld, 0, reqNew, 1, reqOld.length);

        ResourceLocation reqId = new ResourceLocation("firefly8",
                TO_UUID.get(id).toString());
        /*event.addCriterion(reqId, new ConsumeItemTrigger.TriggerInstance(EntityPredicate.Composite.ANY,
                new ItemPredicate(TO_ITEM.get(id),
                        null, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY,
                        EnchantmentPredicate.NONE, EnchantmentPredicate.NONE, null, NbtPredicate.ANY)));*/
        event.addCriterion(reqId, TO_TRIGGER.get(id).get());
        reqNew[0] = new String[] {reqId.toString()};
        event.setRequirements(reqNew);
    }
}
