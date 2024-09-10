package top.xdi8.mod.firefly8.item;

import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.fuel.FuelRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import org.featurehouse.mcmod.spm.platform.api.reg.PlatformRegister;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import top.xdi8.mod.firefly8.entity.FireflyEntityTypes;
import top.xdi8.mod.firefly8.item.indium.*;
import top.xdi8.mod.firefly8.item.symbol.SymbolStoneBlockItem;
import top.xdi8.mod.firefly8.item.symbol.Xdi8TotemItem;
import top.xdi8.mod.firefly8.item.tint.*;
import top.xdi8.mod.firefly8.util.InternalRegistryLogWrapper;

public final class FireflyItems {
    public static final InternalRegistryLogWrapper LOG_WRAPPER = InternalRegistryLogWrapper.firefly8("items");

    private FireflyItems() {}

    public static final CreativeModeTab TAB = CreativeTabRegistry.create(Component.translatable("itemGroup.firefly8.firefly8"),
            () -> FireflyItems.XDI8AHO_ICON.get().getDefaultInstance());

    public static final RegistrySupplier<Item>
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
            BUNDLER,
            SYMBOL_STONE_NN,

            CEDAR_BUTTON,
            CEDAR_DOOR,
            CEDAR_FENCE,
            CEDAR_FENCE_GATE,
            CEDAR_LEAVES,
            CEDAR_LOG,
            STRIPPED_CEDAR_LOG,
            CEDAR_PLANKS,
            CEDAR_PRESSURE_PLATE,
            CEDAR_SAPLING,
            CEDAR_SIGN,
            CEDAR_SLAB,
            CEDAR_STAIRS,
            CEDAR_TRAPDOOR,
            CEDAR_WOOD,
            STRIPPED_CEDAR_WOOD;

    static {
        var reg = PlatformRegister.of("firefly8");
        INDIUM_INGOT = reg.item("indium_ingot",
                () -> new Item(defaultProp()));
        INDIUM_NUGGET = reg.item("indium_nugget",
                () -> new Item(defaultProp()));
        INDIUM_AXE = reg.item("indium_axe",
                () -> new IndiumAxeItem(defaultProp()));
        INDIUM_HOE = reg.item("indium_hoe",
                () -> new IndiumHoeItem(defaultProp()));
        INDIUM_PICKAXE = reg.item("indium_pickaxe",
                () -> new IndiumPickaxeItem(defaultProp()));
        INDIUM_SHOVEL = reg.item("indium_shovel",
                () -> new IndiumShovelItem(defaultProp()));
        INDIUM_SWORD = reg.item("indium_sword",
                () -> new IndiumSwordItem(defaultProp()));
        INDIUM_CHISEL = reg.item("indium_chisel",
                () -> new IndiumChiselItem(defaultProp()
                        .durability(30)
                ));
        INDIUM_BLOCK = reg.item("indium_block",
                () -> new BlockItem(FireflyBlocks.INDIUM_BLOCK.get(), defaultProp()));
        INDIUM_ORE_BLOCK = reg.item("indium_ore",
                () -> new BlockItem(FireflyBlocks.INDIUM_ORE_BLOCK.get(), defaultProp()));
        DEEPSLATE_INDIUM_ORE_BLOCK = reg.item("deepslate_indium_ore",
                () -> new BlockItem(FireflyBlocks.DEEPSLATE_INDIUM_ORE_BLOCK.get(), defaultProp()));
        SYMBOL_STONE_BRICKS = reg.item("symbol_stone_bricks",
                () -> new BlockItem(FireflyBlocks.SYMBOL_STONE_BRICKS.get(), defaultProp()));

        XDI8AHO_ICON = reg.item("xdi8aho",
                () -> new Xdi8TotemItem(defaultProp()));
        FIREFLY_SPAWN_EGG = reg.item("firefly_spawn_egg", () ->
                new ArchitecturySpawnEggItem(FireflyEntityTypes.FIREFLY,
                        0x000000, 0x00f500,
                        defaultProp()));
        XDI8AHO_PORTAL_CORE_BLOCK = reg.item("xdi8aho_portal_core",
                () -> new BlockItem(FireflyBlocks.XDI8AHO_PORTAL_CORE_BLOCK.get(), defaultProp()));
        XDI8AHO_PORTAL_TOP_BLOCK = reg.item("xdi8aho_torch_top",
                () -> new BlockItem(FireflyBlocks.XDI8AHO_PORTAL_TOP_BLOCK.get(), defaultProp()));
        XDI8AHO_BACK_PORTAL_CORE_BLOCK = reg.item("xdi8aho_back_portal_core",
                () -> new BlockItem(FireflyBlocks.XDI8AHO_BACK_PORTAL_CORE_BLOCK.get(), defaultProp()));
        XDI8_TABLE = reg.item("xdi8_table",
                () -> new BlockItem(FireflyBlocks.XDI8_TABLE.get(), defaultProp()));

        // Bottles
        TINTED_GLASS_BOTTLE = reg.item("tinted_glass_bottle", () ->
                new TintedGlassBottleItem(defaultProp()));
        TINTED_POTION = reg.item("tinted_potion", () ->
                new TintedPotionItem(defaultProp()));
        TINTED_HONEY_BOTTLE = reg.item("tinted_honey_bottle", () ->
                new TintedHoneyBottleItem(defaultProp()
                        .craftRemainder(TINTED_GLASS_BOTTLE.get())
                        .food(Foods.HONEY_BOTTLE)
                        .stacksTo(16)
                ));
        TINTED_SPLASH_POTION = reg.item("tinted_splash_potion", () ->
                new TintedSplashPotionItem(defaultProp()));
        TINTED_LINGERING_POTION = reg.item("tinted_lingering_potion", () ->
                new TintedLingeringPotionItem(defaultProp()));
        TINTED_DRAGON_BREATH = reg.item("tinted_dragon_breath", () ->
                new Item(defaultProp()
                        .craftRemainder(TINTED_GLASS_BOTTLE.get())
                        .rarity(Rarity.UNCOMMON)));
        TINTED_FIREFLY_BOTTLE = reg.item("tinted_firefly_bottle", () ->
                new TintedFireflyBottleItem(defaultProp().stacksTo(1)));

        BUNDLER = reg.item("bundler", BundlerItem::new);
        SymbolStoneBlockItem.registerAll((id, sup) -> reg.item(id, sup::get));
        DARK_SYMBOL_STONE = reg.item("dark_symbol_stone", () ->
                new BlockItem(FireflyBlocks.DARK_SYMBOL_STONE.get(),
                        defaultProp().fireResistant()));
        SYMBOL_STONE_NN = reg.item("symbol_stone_nn", () ->
                new BlockItem(FireflyBlocks.SYMBOL_STONE_NN.get(),
                        defaultProp().rarity(Rarity.UNCOMMON)));

        CEDAR_BUTTON = reg.item("cedar_button", () ->
                new BlockItem(FireflyBlocks.CEDAR_BUTTON.get(),
                        defaultProp()));
        CEDAR_DOOR = reg.item("cedar_door", () ->
                new BlockItem(FireflyBlocks.CEDAR_DOOR.get(),
                        defaultProp()));
        CEDAR_FENCE = reg.item("cedar_fence", () ->
                new BlockItem(FireflyBlocks.CEDAR_FENCE.get(),
                        defaultProp()));
        CEDAR_FENCE_GATE = reg.item("cedar_fence_gate", () ->
                new BlockItem(FireflyBlocks.CEDAR_FENCE_GATE.get(),
                        defaultProp()));
        CEDAR_LEAVES = reg.item("cedar_leaves", () ->
                new BlockItem(FireflyBlocks.CEDAR_LEAVES.get(),
                        defaultProp()));
        CEDAR_LOG = reg.item("cedar_log", () ->
                new BlockItem(FireflyBlocks.CEDAR_LOG.get(),
                        defaultProp()));
        STRIPPED_CEDAR_LOG = reg.item("stripped_cedar_log", () ->
                new BlockItem(FireflyBlocks.STRIPPED_CEDAR_LOG.get(),
                        defaultProp()));
        CEDAR_PLANKS = reg.item("cedar_planks", () ->
                new BlockItem(FireflyBlocks.CEDAR_PLANKS.get(),
                        defaultProp()));
        CEDAR_PRESSURE_PLATE = reg.item("cedar_pressure_plate", () ->
                new BlockItem(FireflyBlocks.CEDAR_PRESSURE_PLATE.get(),
                        defaultProp()));
        CEDAR_SAPLING = reg.item("cedar_sapling", () ->
                new BlockItem(FireflyBlocks.CEDAR_SAPLING.get(),
                        defaultProp()));
        CEDAR_SIGN = reg.item("cedar_sign", () ->
                new BlockItem(FireflyBlocks.CEDAR_SIGN.get(),
                        defaultProp()));
        CEDAR_SLAB = reg.item("cedar_slab", () ->
                new BlockItem(FireflyBlocks.CEDAR_SLAB.get(),
                        defaultProp()));
        CEDAR_STAIRS = reg.item("cedar_stairs", () ->
                new BlockItem(FireflyBlocks.CEDAR_STAIRS.get(),
                        defaultProp()));
        CEDAR_TRAPDOOR = reg.item("cedar_trapdoor", () ->
                new BlockItem(FireflyBlocks.CEDAR_TRAPDOOR.get(),
                        defaultProp()));
        CEDAR_WOOD = reg.item("cedar_wood", () ->
                new BlockItem(FireflyBlocks.CEDAR_WOOD.get(),
                        defaultProp()));
        STRIPPED_CEDAR_WOOD = reg.item("stripped_cedar_wood", () ->
                new BlockItem(FireflyBlocks.STRIPPED_CEDAR_WOOD.get(),
                        defaultProp()));
    }

    static Item.Properties defaultProp() {
        return new Item.Properties().tab(TAB);
    }

    public static void registerFuels(){
        FuelRegistry.register(300, CEDAR_FENCE.get());
        FuelRegistry.register(300, CEDAR_FENCE_GATE.get());
    }
}
