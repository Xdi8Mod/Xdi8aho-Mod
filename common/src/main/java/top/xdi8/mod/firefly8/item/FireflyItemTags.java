package top.xdi8.mod.firefly8.item;

import io.github.qwerty770.mcmod.xdi8.api.InternalRegistryLogWrapper;
import io.github.qwerty770.mcmod.xdi8.util.tag.TagContainer;
import net.minecraft.world.item.Item;

import static io.github.qwerty770.mcmod.xdi8.util.registries.RegistryHelper.itemTag;

public class FireflyItemTags {
    public static final InternalRegistryLogWrapper LOG_WRAPPER = InternalRegistryLogWrapper.firefly8("blocks");
    public static final TagContainer<Item> TOTEM = itemTag("xdi8_totem");
    public static final TagContainer<Item> SYMBOL_STONES = itemTag("symbol_stones");
    public static final TagContainer<Item> TINTED_DRAGON_BREATH = itemTag("tinted_dragon_breath");
    public static final TagContainer<Item> TINTED_HONEY_BOTTLES = itemTag("tinted_honey_bottles");
}
