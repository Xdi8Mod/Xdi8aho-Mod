package top.xdi8.mod.firefly8.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public final class FireflyItems {
    public static final DeferredRegister<Item> REGISTRY =
        DeferredRegister.create(ForgeRegistries.ITEMS, "firefly8");
    private FireflyItems() {}

    public static final CreativeModeTab TAB = new CreativeModeTab("firefly8") {
        public @NotNull ItemStack makeIcon() {
            return ItemStack.EMPTY; // TODO
        }
    };

    public static final RegistryObject<Item> TINTED_GLASS_BOTTLE,
            TINTED_POTION,
            TINTED_HONEY_BOTTLE,
            TINTED_SPLASH_POTION,
            TINTED_LINGERING_POTION;

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
    }
}
