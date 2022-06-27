package top.xdi8.mod.firefly8.item;

import net.minecraft.world.food.Foods;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.entity.FireflyEntityTypes;

public final class FireflyItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, "firefly8");
    private FireflyItems() {}

    public static final CreativeModeTab TAB = new CreativeModeTab("firefly8") {
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(XDI8AHO_ICON.get());
        }
    };

    public static final RegistryObject<Item> TINTED_GLASS_BOTTLE,
            TINTED_POTION,
            TINTED_HONEY_BOTTLE,
            TINTED_SPLASH_POTION,
            TINTED_LINGERING_POTION,
            XDI8AHO_ICON,
            FIREFLY_SPAWN_EGG;

    static {
        TINTED_GLASS_BOTTLE = REGISTRY.register("tinted_glass_bottle", () ->
            new TintedGlassBottleItem(defaultProp()));
        TINTED_POTION = REGISTRY.register("tinted_potion", () ->
            new TintedPotionItem(defaultProp()));
        TINTED_HONEY_BOTTLE = REGISTRY.register("tinted_honey_bottle", () ->
            new TintedHoneyBottleItem(defaultProp()
                    .craftRemainder(TINTED_GLASS_BOTTLE.get())
                    .food(Foods.HONEY_BOTTLE)
                    .stacksTo(16)
            ));
        TINTED_SPLASH_POTION = REGISTRY.register("tinted_splash_potion", () ->
            new TintedSplashPotionItem(defaultProp()));
        TINTED_LINGERING_POTION = REGISTRY.register("tinted_lingering_potion", () ->
            new TintedLingeringPotionItem(defaultProp()));
        XDI8AHO_ICON = REGISTRY.register("xdi8aho", () ->
                new Item(new Item.Properties()));
        FIREFLY_SPAWN_EGG = REGISTRY.register("firefly_spawn_egg", () ->
                new ForgeSpawnEggItem(FireflyEntityTypes.FIREFLY,
                        0x000000, 0x00f500,
                        defaultProp()));
    }

    static Item.Properties defaultProp() { return new Item.Properties().tab(TAB); }
}
