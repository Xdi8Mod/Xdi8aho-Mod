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
import top.xdi8.mod.firefly8.item.indium.*;
import top.xdi8.mod.firefly8.item.symbol.SymbolStoneBlockItem;
import top.xdi8.mod.firefly8.item.symbol.Xdi8TotemItem;
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

    public static final RegistryObject<Item> 
            INDIUM_INGOT,
            INDIUM_NUGGET,
            INDIUM_AXE,
            INDIUM_HOE,
            INDIUM_PICKAXE,
            INDIUM_SHOVEL,
            INDIUM_SWORD,
            INDIUM_CHISEL,
            INDIUM_BLOCK,
            INDIUM_ORE_BLOCK,
            DEEPSLATE_INDIUM_ORE_BLOCK,
            SYMBOL_STONE_BRICKS,

            XDI8AHO_ICON,
            FIREFLY_SPAWN_EGG,

            XDI8AHO_PORTAL_CORE_BLOCK,
            XDI8AHO_PORTAL_TOP_BLOCK,
            XDI8AHO_BACK_PORTAL_CORE_BLOCK,
            XDI8_TABLE,

            TINTED_GLASS_BOTTLE,
            TINTED_POTION,
            TINTED_HONEY_BOTTLE,
            TINTED_SPLASH_POTION,
            TINTED_LINGERING_POTION,
            TINTED_DRAGON_BREATH,
            TINTED_FIREFLY_BOTTLE,

            DARK_SYMBOL_STONE,
            BUNDLER;

    static {
        INDIUM_INGOT = REGISTRY.register("indium_ingot",
                () -> new Item(defaultProp()));
        INDIUM_NUGGET = REGISTRY.register("indium_nugget",
                () -> new Item(defaultProp()));
        INDIUM_AXE = REGISTRY.register("indium_axe",
                () -> new IndiumAxeItem(defaultProp()));
        INDIUM_HOE = REGISTRY.register("indium_hoe",
                () -> new IndiumHoeItem(defaultProp()));
        INDIUM_PICKAXE = REGISTRY.register("indium_pickaxe",
                () -> new IndiumPickaxeItem(defaultProp()));
        INDIUM_SHOVEL = REGISTRY.register("indium_shovel",
                () -> new IndiumShovelItem(defaultProp()));
        INDIUM_SWORD = REGISTRY.register("indium_sword",
                () -> new IndiumSwordItem(defaultProp()));
        INDIUM_CHISEL = REGISTRY.register("indium_chisel",
                () -> new IndiumChiselItem(defaultProp()
                        .durability(30)
                ));
        INDIUM_BLOCK = REGISTRY.register("indium_block",
                () -> new BlockItem(FireflyBlocks.INDIUM_BLOCK.get(), defaultProp()));
        INDIUM_ORE_BLOCK = REGISTRY.register("indium_ore",
                () -> new BlockItem(FireflyBlocks.INDIUM_ORE_BLOCK.get(), defaultProp()));
        DEEPSLATE_INDIUM_ORE_BLOCK = REGISTRY.register("deepslate_indium_ore",
                () -> new BlockItem(FireflyBlocks.DEEPSLATE_INDIUM_ORE_BLOCK.get(), defaultProp()));
        SYMBOL_STONE_BRICKS = REGISTRY.register("symbol_stone_bricks",
                () -> new BlockItem(FireflyBlocks.SYMBOL_STONE_BRICKS.get(), defaultProp()));

        XDI8AHO_ICON = REGISTRY.register("xdi8aho",
                () -> new Xdi8TotemItem(defaultProp()));
        FIREFLY_SPAWN_EGG = REGISTRY.register("firefly_spawn_egg", () ->
                new ForgeSpawnEggItem(FireflyEntityTypes.FIREFLY,
                        0x000000, 0x00f500,
                        defaultProp()));
                        
        XDI8AHO_PORTAL_CORE_BLOCK = REGISTRY.register("xdi8aho_portal_core",
                () -> new BlockItem(FireflyBlocks.XDI8AHO_PORTAL_CORE_BLOCK.get(), defaultProp()));
        XDI8AHO_PORTAL_TOP_BLOCK = REGISTRY.register("xdi8aho_torch_top",
                () -> new BlockItem(FireflyBlocks.XDI8AHO_PORTAL_TOP_BLOCK.get(), defaultProp()));
        XDI8AHO_BACK_PORTAL_CORE_BLOCK = REGISTRY.register("xdi8aho_back_portal_core",
                () -> new BlockItem(FireflyBlocks.XDI8AHO_BACK_PORTAL_CORE_BLOCK.get(), defaultProp()));
        XDI8_TABLE = REGISTRY.register("xdi8_table",
                () -> new BlockItem(FireflyBlocks.XDI8_TABLE.get(), defaultProp()));

        // Bottles
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
                new TintedFireflyBottleItem(defaultProp().stacksTo(1)));

        BUNDLER = REGISTRY.register("bundler", BundlerItem::new);
        SymbolStoneBlockItem.registerAll(REGISTRY::register);
        DARK_SYMBOL_STONE = REGISTRY.register("dark_symbol_stone", () ->
                new BlockItem(FireflyBlocks.DARK_SYMBOL_STONE.get(),
                        defaultProp().fireResistant()));
    }

    static Item.Properties defaultProp() {
        return new Item.Properties().tab(TAB);
    }
}
