package top.xdi8.mod.firefly8.item;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.featurehouse.mcmod.spm.platform.api.reg.PlatformRegister;

public class FireflyItemTags {
    private static final PlatformRegister reg = PlatformRegister.of("firefly8");
    public static final TagKey<Item> TOTEM = reg.itemTag("xdi8_totem");
    public static final TagKey<Item> SYMBOL_STONES = reg.itemTag("symbol_stones");
    public static final TagKey<Item> TINTED_DRAGON_BREATH = reg.itemTag("tinted_dragon_breath");
    public static final TagKey<Item> TINTED_HONEY_BOTTLES = reg.itemTag("tinted_honey_bottles");
}
