package top.xdi8.mod.firefly8.item.tint.advancement;

import com.google.common.collect.ImmutableMap;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.xdi8.mod.firefly8.item.FireflyItemTags;

import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = "firefly8")
public class VanillaAdvancements {
    private static final ResourceLocation SAFELY_HARVEST_MONEY = new ResourceLocation("husbandry/safely_harvest_honey");
    private static final ResourceLocation DRAGON_BREATH = new ResourceLocation("end/dragon_breath");

    private static final Map<ResourceLocation, TagKey<Item>> TO_ITEM = ImmutableMap.of(
            SAFELY_HARVEST_MONEY, FireflyItemTags.TINTED_HONEY_BOTTLES,
            DRAGON_BREATH, FireflyItemTags.TINTED_DRAGON_BREATH);
    private static final Map<ResourceLocation, UUID> TO_UUID = ImmutableMap.of(
            SAFELY_HARVEST_MONEY, UUID.fromString("b0ec73e8-c187-4f25-a83c-cc42263282b7"),
            DRAGON_BREATH, UUID.fromString("fe4d3782-e9b4-431a-a4bc-5b508852ef3c"));

    @SubscribeEvent
    public static void patchTintedItem(AdvancementLoadingEvent event) {
        final ResourceLocation id = event.getId();
        if (!TO_ITEM.containsKey(id)) return;
        String[][] reqOld = event.getRequirements(),
                   reqNew = new String[reqOld.length + 1][];
        System.arraycopy(reqOld, 0, reqNew, 1, reqOld.length);

        ResourceLocation reqId = new ResourceLocation("firefly8",
                TO_UUID.get(id).toString());
        event.addCriterion(reqId, new ConsumeItemTrigger.TriggerInstance(EntityPredicate.Composite.ANY,
                new ItemPredicate(TO_ITEM.get(id),
                        null, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY,
                        EnchantmentPredicate.NONE, EnchantmentPredicate.NONE, null, NbtPredicate.ANY)));
        reqNew[0] = new String[] {reqId.toString()};
        event.setRequirements(reqNew);
    }
}
