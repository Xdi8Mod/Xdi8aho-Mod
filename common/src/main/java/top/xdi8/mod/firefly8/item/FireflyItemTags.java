package top.xdi8.mod.firefly8.item;

import io.github.qwerty770.mcmod.xdi8.registries.InternalRegistryLogWrapper;
import io.github.qwerty770.mcmod.xdi8.tag.TagContainer;
import net.minecraft.world.item.Item;

import static io.github.qwerty770.mcmod.xdi8.registries.RegistryHelper.itemTag;

public class FireflyItemTags {
    public static final InternalRegistryLogWrapper LOG_WRAPPER = InternalRegistryLogWrapper.firefly8("blocks");
    public static final TagContainer<Item> INDIUM_TOOL_MATERIALS = itemTag("indium_tool_materials");
    public static final TagContainer<Item> SYMBOL_STONES = itemTag("symbol_stones");
    public static final TagContainer<Item> TINTED_DRAGON_BREATH = itemTag("tinted_dragon_breath");
    public static final TagContainer<Item> TINTED_HONEY_BOTTLES = itemTag("tinted_honey_bottles");
    public static final TagContainer<Item> TOTEM = itemTag("xdi8_totem");
}
