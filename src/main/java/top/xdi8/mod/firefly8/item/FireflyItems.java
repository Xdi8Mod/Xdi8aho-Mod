package top.xdi8.mod.firefly8.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.entity.FireflyEntityTypes;
import top.xdi8.mod.firefly8.item.technical.Xdi8AhoItem;

public final class FireflyItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, "firefly8");
    private FireflyItems() {}

    public static final CreativeModeTab TAB = new CreativeModeTab("firefly8") {
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(Xdi8Aho.get());
        }
    };

    public static final RegistryObject<Item> TINTED_GLASS_BOTTLE,
            TINTED_POTION,
            TINTED_HONEY_BOTTLE,
            TINTED_SPLASH_POTION,
            TINTED_LINGERING_POTION,
            Xdi8Aho;

    static {
        TINTED_GLASS_BOTTLE = REGISTRY.register("tinted_glass_bottle", () ->
            new TintedGlassBottleItem(new Item.Properties().tab(TAB)));
        TINTED_POTION = REGISTRY.register("tinted_potion", () ->
            new TintedPotionItem(new Item.Properties().tab(TAB)));
        TINTED_HONEY_BOTTLE = REGISTRY.register("tinted_honey_bottle", () ->
            new TintedHoneyBottleItem(new Item.Properties().tab(TAB)));
        TINTED_SPLASH_POTION = REGISTRY.register("tinted_splash_potion", () ->
            new TintedSplashPotionItem(new Item.Properties().tab(TAB)));
        TINTED_LINGERING_POTION = REGISTRY.register("tinted_lingering_potion", () ->
            new TintedLingeringPotionItem(new Item.Properties().tab(TAB)));
        Xdi8Aho = REGISTRY.register("xdi8aho", () ->
                new Xdi8AhoItem(new Item.Properties()));
    }
    public static final RegistryObject<ForgeSpawnEggItem> FIREFLY_SPAWN_EGG = REGISTRY.register("firefly_spawn_egg", () ->
            new ForgeSpawnEggItem(FireflyEntityTypes.FIREFLY, 0x000000, 0x00f500, new Item.Properties().tab(TAB)));
}
