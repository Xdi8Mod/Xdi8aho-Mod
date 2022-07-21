package top.xdi8.mod.firefly8.item;

import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import top.xdi8.mod.firefly8.entity.FireflyEntityTypes;
import top.xdi8.mod.firefly8.item.indium.IndiumIngotItem;
import top.xdi8.mod.firefly8.item.indium.IndiumNuggetItem;
import top.xdi8.mod.firefly8.item.tint.*;

public final class FireflyItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, "firefly8");

    private FireflyItems() {
    }

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
            TINTED_DRAGON_BREATH,
            TINTED_FIREFLY_BOTTLE,
            XDI8AHO_ICON,
            FIREFLY_SPAWN_EGG,
            BUNDLER,
            XDI8AHO_PORTAL_CORE_BLOCK,
            XDI8AHO_PORTAL_TOP_BLOCK,
            INDIUM_INGOT,
            INDIUM_NUGGET,
            INDIUM_BLOCK,
            INDIUM_ORE_BLOCK,
            DEEPSLATE_INDIUM_ORE_BLOCK;

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
        TINTED_DRAGON_BREATH = REGISTRY.register("tinted_dragon_breath", () ->
                new Item(defaultProp()
                        .craftRemainder(TINTED_GLASS_BOTTLE.get())
                        .rarity(Rarity.UNCOMMON)));
        TINTED_FIREFLY_BOTTLE = REGISTRY.register("tinted_firefly_bottle", () ->
                new TintedFireflyBottleItem(defaultProp()));
        XDI8AHO_ICON = REGISTRY.register("xdi8aho", () ->
                new Item(new Item.Properties()));
        FIREFLY_SPAWN_EGG = REGISTRY.register("firefly_spawn_egg", () ->
                new ForgeSpawnEggItem(FireflyEntityTypes.FIREFLY,
                        0x000000, 0x00f500,
                        defaultProp()));
        BUNDLER = REGISTRY.register("bundler", BundlerItem::new);
        INDIUM_INGOT = REGISTRY.register("indium_ingot",
                () -> new IndiumIngotItem(defaultProp()));
        INDIUM_NUGGET = REGISTRY.register("indium_nugget",
                () -> new IndiumNuggetItem(defaultProp()));
        // Blocks
        XDI8AHO_PORTAL_CORE_BLOCK = REGISTRY.register("xdi8aho_portal_core",
                () -> new BlockItem(FireflyBlocks.XDI8AHO_PORTAL_CORE_BLOCK.get(), defaultProp()));
        XDI8AHO_PORTAL_TOP_BLOCK = REGISTRY.register("xdi8aho_torch_top",
                () -> new BlockItem(FireflyBlocks.XDI8AHO_PORTAL_TOP_BLOCK.get(), defaultProp()));
        INDIUM_BLOCK = REGISTRY.register("indium_block",
                () -> new BlockItem(FireflyBlocks.INDIUM_BLOCK.get(), defaultProp()));
        INDIUM_ORE_BLOCK = REGISTRY.register("indium_ore",
                () -> new BlockItem(FireflyBlocks.INDIUM_ORE_BLOCK.get(), defaultProp()));
        DEEPSLATE_INDIUM_ORE_BLOCK = REGISTRY.register("deepslate_indium_ore",
                () -> new BlockItem(FireflyBlocks.DEEPSLATE_INDIUM_ORE_BLOCK.get(), defaultProp()));
    }

    static Item.Properties defaultProp() {
        return new Item.Properties().tab(TAB);
    }
}
