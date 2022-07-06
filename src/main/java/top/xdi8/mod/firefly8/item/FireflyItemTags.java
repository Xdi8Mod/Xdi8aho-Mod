package top.xdi8.mod.firefly8.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class FireflyItemTags {
    public static final TagKey<Item>
            TINTED_DRAGON_BREATH = create("tinted_dragon_breath"),
            TINTED_HONEY_BOTTLES = create("tinted_honey_bottles"),
            BALANCED_DIET = create("balanced_diet");

    private static TagKey<Item> create(String path) {
        return ItemTags.create(new ResourceLocation("firefly8", path));
    }
}
