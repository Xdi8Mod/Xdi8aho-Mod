package top.xdi8.mod.firefly8.item;

import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.fuel.FuelRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.qwerty770.mcmod.xdi8.registries.RegistryHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.Consumables;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import top.xdi8.mod.firefly8.entity.FireflyEntityTypes;
import top.xdi8.mod.firefly8.item.indium.*;
import top.xdi8.mod.firefly8.item.symbol.SymbolStoneBlockItem;
import top.xdi8.mod.firefly8.item.symbol.Xdi8TotemItem;
import top.xdi8.mod.firefly8.item.tint.*;
import io.github.qwerty770.mcmod.xdi8.registries.InternalRegistryLogWrapper;

import java.util.function.Function;
import java.util.function.Supplier;

import static io.github.qwerty770.mcmod.xdi8.registries.RegistryHelper.*;

public final class FireflyItems {
    public static final InternalRegistryLogWrapper LOG_WRAPPER = InternalRegistryLogWrapper.firefly8("items");
    public static final CreativeModeTab FIREFLY8_TAB = CreativeTabRegistry.create(Component.translatable("itemGroup.firefly8.firefly8"),
            () -> FireflyItems.XDI8AHO_ICON.get().getDefaultInstance());
    public static final RegistrySupplier<CreativeModeTab> FIREFLY8_TAB_SUPPLIER = creativeModeTab("firefly8_items", FIREFLY8_TAB);

    public static final RegistrySupplier<Item> INDIUM_INGOT;
    public static final RegistrySupplier<Item> INDIUM_NUGGET;
    public static final RegistrySupplier<Item> INDIUM_AXE;
    public static final RegistrySupplier<Item> INDIUM_CHISEL;
    public static final RegistrySupplier<Item> INDIUM_HOE;
    public static final RegistrySupplier<Item> INDIUM_PICKAXE;
    public static final RegistrySupplier<Item> INDIUM_SHOVEL;
    public static final RegistrySupplier<Item> INDIUM_SWORD;
    public static final RegistrySupplier<BlockItem> INDIUM_BLOCK;
    public static final RegistrySupplier<BlockItem> INDIUM_ORE_BLOCK;
    public static final RegistrySupplier<BlockItem> DEEPSLATE_INDIUM_ORE_BLOCK;

    public static final RegistrySupplier<Item> BUNDLER;
    public static final RegistrySupplier<Item> XDI8AHO_ICON;
    public static final RegistrySupplier<Item> FIREFLY_SPAWN_EGG;

    public static final RegistrySupplier<BlockItem> XDI8AHO_PORTAL_CORE_BLOCK;
    public static final RegistrySupplier<BlockItem> XDI8AHO_PORTAL_TOP_BLOCK;
    public static final RegistrySupplier<BlockItem> XDI8AHO_BACK_PORTAL_CORE_BLOCK;
    public static final RegistrySupplier<BlockItem> XDI8_TABLE;

    public static final RegistrySupplier<Item> TINTED_GLASS_BOTTLE;
    public static final RegistrySupplier<Item> TINTED_POTION;
    public static final RegistrySupplier<Item> TINTED_HONEY_BOTTLE;
    public static final RegistrySupplier<Item> TINTED_SPLASH_POTION;
    public static final RegistrySupplier<Item> TINTED_LINGERING_POTION;
    public static final RegistrySupplier<Item> TINTED_DRAGON_BREATH;
    public static final RegistrySupplier<Item> TINTED_FIREFLY_BOTTLE;

    public static final RegistrySupplier<BlockItem> DARK_SYMBOL_STONE;
    public static final RegistrySupplier<BlockItem> SYMBOL_STONE_BRICKS;
    public static final RegistrySupplier<BlockItem> SYMBOL_STONE_BRICK_SLAB;
    public static final RegistrySupplier<BlockItem> SYMBOL_STONE_BRICK_STAIRS;
    public static final RegistrySupplier<BlockItem> SYMBOL_STONE_NN;

    public static final RegistrySupplier<BlockItem> CEDAR_BUTTON;
    public static final RegistrySupplier<BlockItem> CEDAR_DOOR;
    public static final RegistrySupplier<BlockItem> CEDAR_FENCE;
    public static final RegistrySupplier<BlockItem> CEDAR_FENCE_GATE;
    public static final RegistrySupplier<BlockItem> CEDAR_LEAVES;
    public static final RegistrySupplier<BlockItem> CEDAR_LOG;
    public static final RegistrySupplier<BlockItem> CEDAR_PLANKS;
    public static final RegistrySupplier<BlockItem> CEDAR_PRESSURE_PLATE;
    public static final RegistrySupplier<BlockItem> CEDAR_SAPLING;
    public static final RegistrySupplier<BlockItem> CEDAR_SIGN;
    public static final RegistrySupplier<BlockItem> CEDAR_SLAB;
    public static final RegistrySupplier<BlockItem> CEDAR_STAIRS;
    public static final RegistrySupplier<BlockItem> CEDAR_TRAPDOOR;
    public static final RegistrySupplier<BlockItem> CEDAR_WOOD;
    public static final RegistrySupplier<BlockItem> POTTED_CEDAR_SAPLING;
    public static final RegistrySupplier<BlockItem> STRIPPED_CEDAR_LOG;
    public static final RegistrySupplier<BlockItem> STRIPPED_CEDAR_WOOD;

    static {
        INDIUM_INGOT = item("indium_ingot");
        INDIUM_NUGGET = item("indium_nugget");
        INDIUM_AXE = item("indium_axe", IndiumAxeItem::new);
        INDIUM_CHISEL = item("indium_chisel", IndiumChiselItem::new, defaultProp().durability(30));
        INDIUM_HOE = item("indium_hoe", IndiumHoeItem::new);
        INDIUM_PICKAXE = item("indium_pickaxe", IndiumPickaxeItem::new);
        INDIUM_SHOVEL = item("indium_shovel", IndiumShovelItem::new);
        INDIUM_SWORD = item("indium_sword", IndiumSwordItem::new);
        INDIUM_BLOCK = blockItem("indium_block", FireflyBlocks.INDIUM_BLOCK, defaultProp());
        INDIUM_ORE_BLOCK = blockItem("indium_ore", FireflyBlocks.INDIUM_ORE_BLOCK, defaultProp());
        DEEPSLATE_INDIUM_ORE_BLOCK = blockItem("deepslate_indium_ore", FireflyBlocks.DEEPSLATE_INDIUM_ORE_BLOCK, defaultProp());

        BUNDLER = item("bundler", BundlerItem::new, defaultProp().stacksTo(1));
        XDI8AHO_ICON = item("xdi8aho", Xdi8TotemItem::new);
        FIREFLY_SPAWN_EGG = item("firefly_spawn_egg", (properties) ->
                new ArchitecturySpawnEggItem(FireflyEntityTypes.FIREFLY, properties), defaultProp());
        XDI8AHO_PORTAL_CORE_BLOCK = blockItem("xdi8aho_portal_core", FireflyBlocks.XDI8AHO_PORTAL_CORE_BLOCK, defaultProp());
        XDI8AHO_PORTAL_TOP_BLOCK = blockItem("xdi8aho_torch_top", FireflyBlocks.XDI8AHO_PORTAL_TOP_BLOCK, defaultProp());
        XDI8AHO_BACK_PORTAL_CORE_BLOCK = blockItem("xdi8aho_back_portal_core", FireflyBlocks.XDI8AHO_BACK_PORTAL_CORE_BLOCK, defaultProp());
        XDI8_TABLE = blockItem("xdi8_table", FireflyBlocks.XDI8_TABLE, defaultProp());

        // Bottles
        TINTED_GLASS_BOTTLE = item("tinted_glass_bottle", TintedGlassBottleItem::new);
        TINTED_POTION = item("tinted_potion", TintedPotionItem::new);
        TINTED_HONEY_BOTTLE = item("tinted_honey_bottle", TintedHoneyBottleItem::new, () -> defaultProp()
                .craftRemainder(TINTED_GLASS_BOTTLE.get())
                .food(Foods.HONEY_BOTTLE, Consumables.HONEY_BOTTLE)
                .usingConvertsTo(TINTED_GLASS_BOTTLE.get())
                .stacksTo(16));
        TINTED_SPLASH_POTION = item("tinted_splash_potion", TintedSplashPotionItem::new);
        TINTED_LINGERING_POTION = item("tinted_lingering_potion", TintedLingeringPotionItem::new);
        TINTED_DRAGON_BREATH = item("tinted_dragon_breath", Item::new, () -> defaultProp()
                .craftRemainder(TINTED_GLASS_BOTTLE.get())
                .rarity(Rarity.UNCOMMON));
        TINTED_FIREFLY_BOTTLE = item("tinted_firefly_bottle", TintedFireflyBottleItem::new, defaultProp().stacksTo(1));

        SymbolStoneBlockItem.registerAll(RegistryHelper::item);
        DARK_SYMBOL_STONE = blockItem("dark_symbol_stone", FireflyBlocks.DARK_SYMBOL_STONE, defaultProp().fireResistant());
        SYMBOL_STONE_BRICKS = blockItem("symbol_stone_bricks", FireflyBlocks.SYMBOL_STONE_BRICKS, defaultProp());
        SYMBOL_STONE_BRICK_SLAB = blockItem("symbol_stone_brick_slab", FireflyBlocks.SYMBOL_STONE_BRICK_SLAB, defaultProp());
        SYMBOL_STONE_BRICK_STAIRS = blockItem("symbol_stone_brick_stairs", FireflyBlocks.SYMBOL_STONE_BRICK_STAIRS, defaultProp());
        SYMBOL_STONE_NN = blockItem("symbol_stone_nn", FireflyBlocks.SYMBOL_STONE_NN, defaultProp().rarity(Rarity.UNCOMMON));

        CEDAR_BUTTON = blockItem("cedar_button", FireflyBlocks.CEDAR_BUTTON, defaultProp());
        CEDAR_DOOR = blockItem("cedar_door", FireflyBlocks.CEDAR_DOOR, defaultProp());
        CEDAR_FENCE = blockItem("cedar_fence", FireflyBlocks.CEDAR_FENCE, defaultProp());
        CEDAR_FENCE_GATE = blockItem("cedar_fence_gate", FireflyBlocks.CEDAR_FENCE_GATE, defaultProp());
        CEDAR_LEAVES = blockItem("cedar_leaves", FireflyBlocks.CEDAR_LEAVES, defaultProp());
        CEDAR_LOG = blockItem("cedar_log", FireflyBlocks.CEDAR_LOG, defaultProp());
        CEDAR_PLANKS = blockItem("cedar_planks", FireflyBlocks.CEDAR_PLANKS, defaultProp());
        CEDAR_PRESSURE_PLATE = blockItem("cedar_pressure_plate", FireflyBlocks.CEDAR_PRESSURE_PLATE, defaultProp());
        CEDAR_SAPLING = blockItem("cedar_sapling", FireflyBlocks.CEDAR_SAPLING, defaultProp());
        CEDAR_SIGN = blockItem("cedar_sign", FireflyBlocks.CEDAR_SIGN, defaultProp());
        CEDAR_SLAB = blockItem("cedar_slab", FireflyBlocks.CEDAR_SLAB, defaultProp());
        CEDAR_STAIRS = blockItem("cedar_stairs", FireflyBlocks.CEDAR_STAIRS, defaultProp());
        CEDAR_TRAPDOOR = blockItem("cedar_trapdoor", FireflyBlocks.CEDAR_TRAPDOOR, defaultProp());
        CEDAR_WOOD = blockItem("cedar_wood", FireflyBlocks.CEDAR_WOOD, defaultProp());
        POTTED_CEDAR_SAPLING = blockItem("potted_cedar_sapling", FireflyBlocks.POTTED_CEDAR_SAPLING, defaultProp());
        STRIPPED_CEDAR_LOG = blockItem("stripped_cedar_log", FireflyBlocks.STRIPPED_CEDAR_LOG, defaultProp());
        STRIPPED_CEDAR_WOOD = blockItem("stripped_cedar_wood", FireflyBlocks.STRIPPED_CEDAR_WOOD, defaultProp());
    }

    static Item.Properties defaultProp() {
        return new Item.Properties().arch$tab(FIREFLY8_TAB_SUPPLIER);
    }

    static RegistrySupplier<Item> item(String id) {
        return RegistryHelper.defaultItem(id, defaultProp());
    }

    static <I extends Item> RegistrySupplier<I> item(String id, Function<Item.Properties, I> function) {
        return RegistryHelper.item(id, function, defaultProp());
    }

    static <I extends Item> RegistrySupplier<I> item(String id, Function<Item.Properties, I> function, Item.Properties properties) {
        return RegistryHelper.item(id, function, properties);
    }

    static <I extends Item> RegistrySupplier<I> item(String id, Function<Item.Properties, I> function, Supplier<Item.Properties> properties) {
        return RegistryHelper.item(id, function, properties);
    }

    public static void registerFuels() {
        FuelRegistry.register(300, CEDAR_FENCE.get());
        FuelRegistry.register(300, CEDAR_FENCE_GATE.get());
    }
}
