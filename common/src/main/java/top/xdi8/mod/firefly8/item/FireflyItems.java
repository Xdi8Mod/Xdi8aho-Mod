package top.xdi8.mod.firefly8.item;

import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.fuel.FuelRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.qwerty770.mcmod.xdi8.util.registries.RegistryHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import top.xdi8.mod.firefly8.entity.FireflyEntityTypes;
import top.xdi8.mod.firefly8.item.indium.*;
import top.xdi8.mod.firefly8.item.symbol.SymbolStoneBlockItem;
import top.xdi8.mod.firefly8.item.symbol.Xdi8TotemItem;
import top.xdi8.mod.firefly8.item.tint.*;
import io.github.qwerty770.mcmod.xdi8.api.InternalRegistryLogWrapper;

import static io.github.qwerty770.mcmod.xdi8.util.registries.RegistryHelper.*;

public final class FireflyItems {
    public static final InternalRegistryLogWrapper LOG_WRAPPER = InternalRegistryLogWrapper.firefly8("items");

    private FireflyItems() {}

    public static final CreativeModeTab TAB = CreativeTabRegistry.create(Component.translatable("itemGroup.firefly8.firefly8"),
            () -> FireflyItems.XDI8AHO_ICON.get().getDefaultInstance());

    public static final RegistrySupplier<Item> INDIUM_INGOT;
    public static final RegistrySupplier<Item> INDIUM_NUGGET;
    public static final RegistrySupplier<Item> INDIUM_AXE;
    public static final RegistrySupplier<Item> INDIUM_HOE;
    public static final RegistrySupplier<Item> INDIUM_PICKAXE;
    public static final RegistrySupplier<Item> INDIUM_SHOVEL;
    public static final RegistrySupplier<Item> INDIUM_SWORD;
    public static final RegistrySupplier<Item> INDIUM_CHISEL;
    public static final RegistrySupplier<Item> INDIUM_BLOCK;
    public static final RegistrySupplier<Item> INDIUM_ORE_BLOCK;
    public static final RegistrySupplier<Item> DEEPSLATE_INDIUM_ORE_BLOCK;
    public static final RegistrySupplier<Item> SYMBOL_STONE_BRICKS;

    public static final RegistrySupplier<Item> XDI8AHO_ICON;
    public static final RegistrySupplier<Item> FIREFLY_SPAWN_EGG;

    public static final RegistrySupplier<Item> XDI8AHO_PORTAL_CORE_BLOCK;
    public static final RegistrySupplier<Item> XDI8AHO_PORTAL_TOP_BLOCK;
    public static final RegistrySupplier<Item> XDI8AHO_BACK_PORTAL_CORE_BLOCK;
    public static final RegistrySupplier<Item> XDI8_TABLE;

    public static final RegistrySupplier<Item> TINTED_GLASS_BOTTLE;
    public static final RegistrySupplier<Item> TINTED_POTION;
    public static final RegistrySupplier<Item> TINTED_HONEY_BOTTLE;
    public static final RegistrySupplier<Item> TINTED_SPLASH_POTION;
    public static final RegistrySupplier<Item> TINTED_LINGERING_POTION;
    public static final RegistrySupplier<Item> TINTED_DRAGON_BREATH;
    public static final RegistrySupplier<Item> TINTED_FIREFLY_BOTTLE;

    public static final RegistrySupplier<Item> DARK_SYMBOL_STONE;
    public static final RegistrySupplier<Item> BUNDLER;
    public static final RegistrySupplier<Item> SYMBOL_STONE_NN;

    public static final RegistrySupplier<BlockItem> CEDAR_BUTTON;
    public static final RegistrySupplier<BlockItem> CEDAR_DOOR;
    public static final RegistrySupplier<BlockItem> CEDAR_FENCE;
    public static final RegistrySupplier<BlockItem> CEDAR_FENCE_GATE;
    public static final RegistrySupplier<BlockItem> CEDAR_LEAVES;
    public static final RegistrySupplier<BlockItem> CEDAR_LOG;
    public static final RegistrySupplier<BlockItem> STRIPPED_CEDAR_LOG;
    public static final RegistrySupplier<BlockItem> CEDAR_PLANKS;
    public static final RegistrySupplier<BlockItem> CEDAR_PRESSURE_PLATE;
    public static final RegistrySupplier<BlockItem> CEDAR_SAPLING;
    public static final RegistrySupplier<BlockItem> CEDAR_SIGN;
    public static final RegistrySupplier<BlockItem> CEDAR_SLAB;
    public static final RegistrySupplier<BlockItem> CEDAR_STAIRS;
    public static final RegistrySupplier<BlockItem> CEDAR_TRAPDOOR;
    public static final RegistrySupplier<BlockItem> CEDAR_WOOD;
    public static final RegistrySupplier<BlockItem> STRIPPED_CEDAR_WOOD;

    static {
        INDIUM_INGOT = defaultItem("indium_ingot",
                defaultProp());
        INDIUM_NUGGET = defaultItem("indium_nugget",
                defaultProp());
        INDIUM_AXE = item("indium_axe",
                () -> new IndiumAxeItem(defaultProp()));
        INDIUM_HOE = item("indium_hoe",
                () -> new IndiumHoeItem(defaultProp()));
        INDIUM_PICKAXE = item("indium_pickaxe",
                () -> new IndiumPickaxeItem(defaultProp()));
        INDIUM_SHOVEL = item("indium_shovel",
                () -> new IndiumShovelItem(defaultProp()));
        INDIUM_SWORD = item("indium_sword",
                () -> new IndiumSwordItem(defaultProp()));
        INDIUM_CHISEL = item("indium_chisel",
                () -> new IndiumChiselItem(defaultProp()
                        .durability(30)
                ));
        INDIUM_BLOCK = item("indium_block",
                () -> new BlockItem(FireflyBlocks.INDIUM_BLOCK.get(), defaultProp()));
        INDIUM_ORE_BLOCK = item("indium_ore",
                () -> new BlockItem(FireflyBlocks.INDIUM_ORE_BLOCK.get(), defaultProp()));
        DEEPSLATE_INDIUM_ORE_BLOCK = item("deepslate_indium_ore",
                () -> new BlockItem(FireflyBlocks.DEEPSLATE_INDIUM_ORE_BLOCK.get(), defaultProp()));
        SYMBOL_STONE_BRICKS = item("symbol_stone_bricks",
                () -> new BlockItem(FireflyBlocks.SYMBOL_STONE_BRICKS.get(), defaultProp()));

        XDI8AHO_ICON = item("xdi8aho",
                () -> new Xdi8TotemItem(defaultProp()));
        FIREFLY_SPAWN_EGG = item("firefly_spawn_egg", () ->
                new ArchitecturySpawnEggItem(FireflyEntityTypes.FIREFLY,
                        0x000000, 0x00f500,
                        defaultProp()));
        XDI8AHO_PORTAL_CORE_BLOCK = item("xdi8aho_portal_core",
                () -> new BlockItem(FireflyBlocks.XDI8AHO_PORTAL_CORE_BLOCK.get(), defaultProp()));
        XDI8AHO_PORTAL_TOP_BLOCK = item("xdi8aho_torch_top",
                () -> new BlockItem(FireflyBlocks.XDI8AHO_PORTAL_TOP_BLOCK.get(), defaultProp()));
        XDI8AHO_BACK_PORTAL_CORE_BLOCK = item("xdi8aho_back_portal_core",
                () -> new BlockItem(FireflyBlocks.XDI8AHO_BACK_PORTAL_CORE_BLOCK.get(), defaultProp()));
        XDI8_TABLE = item("xdi8_table",
                () -> new BlockItem(FireflyBlocks.XDI8_TABLE.get(), defaultProp()));

        // Bottles
        TINTED_GLASS_BOTTLE = item("tinted_glass_bottle", () ->
                new TintedGlassBottleItem(defaultProp()));
        TINTED_POTION = item("tinted_potion", () ->
                new TintedPotionItem(defaultProp()));
        TINTED_HONEY_BOTTLE = item("tinted_honey_bottle", () ->
                new TintedHoneyBottleItem(defaultProp()
                        .craftRemainder(TINTED_GLASS_BOTTLE.get())
                        .food(Foods.HONEY_BOTTLE)
                        .stacksTo(16)
                ));
        TINTED_SPLASH_POTION = item("tinted_splash_potion", () ->
                new TintedSplashPotionItem(defaultProp()));
        TINTED_LINGERING_POTION = item("tinted_lingering_potion", () ->
                new TintedLingeringPotionItem(defaultProp()));
        TINTED_DRAGON_BREATH = item("tinted_dragon_breath", () ->
                new Item(defaultProp()
                        .craftRemainder(TINTED_GLASS_BOTTLE.get())
                        .rarity(Rarity.UNCOMMON)));
        TINTED_FIREFLY_BOTTLE = item("tinted_firefly_bottle", () ->
                new TintedFireflyBottleItem(defaultProp().stacksTo(1)));

        BUNDLER = item("bundler", BundlerItem::new);
        SymbolStoneBlockItem.registerAll(RegistryHelper::item);
        DARK_SYMBOL_STONE = item("dark_symbol_stone", () ->
                new BlockItem(FireflyBlocks.DARK_SYMBOL_STONE.get(),
                        defaultProp().fireResistant()));
        SYMBOL_STONE_NN = item("symbol_stone_nn", () ->
                new BlockItem(FireflyBlocks.SYMBOL_STONE_NN.get(),
                        defaultProp().rarity(Rarity.UNCOMMON)));

        CEDAR_BUTTON = blockItem("cedar_button", FireflyBlocks.CEDAR_BUTTON, defaultProp());
        CEDAR_DOOR = blockItem("cedar_door", FireflyBlocks.CEDAR_DOOR, defaultProp());
        CEDAR_FENCE = blockItem("cedar_fence", FireflyBlocks.CEDAR_FENCE, defaultProp());
        CEDAR_FENCE_GATE = blockItem("cedar_fence_gate", FireflyBlocks.CEDAR_FENCE_GATE, defaultProp());
        CEDAR_LEAVES = blockItem("cedar_leaves", FireflyBlocks.CEDAR_LEAVES, defaultProp());
        CEDAR_LOG = blockItem("cedar_log", FireflyBlocks.CEDAR_LOG, defaultProp());
        STRIPPED_CEDAR_LOG = blockItem("stripped_cedar_log", FireflyBlocks.STRIPPED_CEDAR_LOG, defaultProp());
        CEDAR_PLANKS = blockItem("cedar_planks", FireflyBlocks.CEDAR_PLANKS, defaultProp());
        CEDAR_PRESSURE_PLATE = blockItem("cedar_pressure_plate", FireflyBlocks.CEDAR_PRESSURE_PLATE, defaultProp());
        CEDAR_SAPLING = blockItem("cedar_sapling", FireflyBlocks.CEDAR_SAPLING, defaultProp());
        CEDAR_SIGN = blockItem("cedar_sign", FireflyBlocks.CEDAR_SIGN, defaultProp());
        CEDAR_SLAB = blockItem("cedar_slab", FireflyBlocks.CEDAR_SLAB, defaultProp());
        CEDAR_STAIRS = blockItem("cedar_stairs", FireflyBlocks.CEDAR_STAIRS, defaultProp());
        CEDAR_TRAPDOOR = blockItem("cedar_trapdoor", FireflyBlocks.CEDAR_TRAPDOOR, defaultProp());
        CEDAR_WOOD = blockItem("cedar_wood", FireflyBlocks.CEDAR_WOOD, defaultProp());
        STRIPPED_CEDAR_WOOD = blockItem("stripped_cedar_wood", FireflyBlocks.STRIPPED_CEDAR_WOOD, defaultProp());
    }

    static Item.Properties defaultProp() {
        return new Item.Properties().arch$tab(TAB);
    }

    public static void registerFuels(){
        FuelRegistry.register(300, CEDAR_FENCE.get());
        FuelRegistry.register(300, CEDAR_FENCE_GATE.get());
    }
}
