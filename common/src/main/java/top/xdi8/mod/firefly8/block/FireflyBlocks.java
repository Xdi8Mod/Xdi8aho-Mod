package top.xdi8.mod.firefly8.block;

import dev.architectury.registry.registries.RegistrySupplier;
import io.github.qwerty770.mcmod.xdi8.api.ResourceLocationTool;
import io.github.qwerty770.mcmod.xdi8.util.registries.RegistryHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import top.xdi8.mod.firefly8.block.redwood.FireflyTreeFeatures;
import top.xdi8.mod.firefly8.block.symbol.SymbolStoneBlock;
import top.xdi8.mod.firefly8.block.symbol.SymbolStoneNNBlock;
import top.xdi8.mod.firefly8.block.symbol.Xdi8TableBlock;
import io.github.qwerty770.mcmod.xdi8.api.InternalRegistryLogWrapper;

import java.util.Optional;

import static io.github.qwerty770.mcmod.xdi8.util.registries.BlockUtils.createLeaves;
import static io.github.qwerty770.mcmod.xdi8.util.registries.BlockUtils.woodenBlock;
import static io.github.qwerty770.mcmod.xdi8.util.registries.RegistryHelper.*;
import static top.xdi8.mod.firefly8.block.Xdi8ahoPortalTopBlock.FIREFLY_COUNT;

public class FireflyBlocks {
    public static final InternalRegistryLogWrapper LOG_WRAPPER = InternalRegistryLogWrapper.firefly8("blocks");
    // TODO: rename cedar -> redwood
    public static final MapColor redwoodColor = MapColor.COLOR_RED;
    public static final BlockSetType redwoodSet = BlockSetType.register(new BlockSetType("xdi8_redwood"));
    public static final WoodType redwoodType = WoodType.register(new WoodType("xdi8_redwood", redwoodSet));

    public static final RegistrySupplier<Block> XDI8AHO_PORTAL_CORE_BLOCK;
    public static final RegistrySupplier<Block> XDI8AHO_PORTAL_TOP_BLOCK;
    public static final RegistrySupplier<Block> XDI8AHO_PORTAL_BLOCK;
    public static final RegistrySupplier<Block> XDI8AHO_BACK_PORTAL_CORE_BLOCK;
    public static final RegistrySupplier<Block> XDI8AHO_BACK_FIRE_BLOCK;
    public static final RegistrySupplier<Block> INDIUM_BLOCK;
    public static final RegistrySupplier<Block> INDIUM_ORE_BLOCK;
    public static final RegistrySupplier<Block> DEEPSLATE_INDIUM_ORE_BLOCK;
    public static final RegistrySupplier<Block> DARK_SYMBOL_STONE;
    public static final RegistrySupplier<Block> SYMBOL_STONE_BRICKS;
    public static final RegistrySupplier<Block> XDI8_TABLE;
    public static final RegistrySupplier<Block> SYMBOL_STONE_NN;

    public static final RegistrySupplier<Block> CEDAR_BUTTON;
    public static final RegistrySupplier<Block> CEDAR_DOOR;
    public static final RegistrySupplier<Block> CEDAR_FENCE;
    public static final RegistrySupplier<Block> CEDAR_FENCE_GATE;
    public static final RegistrySupplier<Block> CEDAR_LEAVES;
    public static final RegistrySupplier<Block> CEDAR_LOG;
    public static final RegistrySupplier<Block> STRIPPED_CEDAR_LOG;
    public static final RegistrySupplier<Block> CEDAR_PLANKS;
    public static final RegistrySupplier<Block> CEDAR_PRESSURE_PLATE;
    public static final RegistrySupplier<Block> CEDAR_SAPLING;
    public static final RegistrySupplier<Block> CEDAR_SIGN;
    public static final RegistrySupplier<Block> CEDAR_SLAB;
    public static final RegistrySupplier<Block> CEDAR_STAIRS;
    public static final RegistrySupplier<Block> CEDAR_TRAPDOOR;
    public static final RegistrySupplier<Block> CEDAR_WOOD;
    public static final RegistrySupplier<Block> STRIPPED_CEDAR_WOOD;
    public static final RegistrySupplier<Block> CEDAR_WALL_SIGN;

    static {
        INDIUM_BLOCK = defaultBlock("indium_block", BlockBehaviour.Properties.of()
                .strength(1.0F, 6.0F)
                .requiresCorrectToolForDrops());
        INDIUM_ORE_BLOCK = defaultBlock("indium_ore", BlockBehaviour.Properties.of()
                .strength(3.0F, 3.0F)
                .requiresCorrectToolForDrops());
        DEEPSLATE_INDIUM_ORE_BLOCK = defaultBlock("deepslate_indium_ore", BlockBehaviour.Properties.of()
                .strength(4.5F, 3.0F)
                .requiresCorrectToolForDrops()
                .sound(SoundType.DEEPSLATE));
        XDI8AHO_PORTAL_CORE_BLOCK = defaultBlock("xdi8aho_portal_core", BlockBehaviour.Properties.of()
                .strength(10F, 1200F)
                .requiresCorrectToolForDrops());
        XDI8AHO_PORTAL_TOP_BLOCK = block("xdi8aho_torch_top", Xdi8ahoPortalTopBlock::new,
                BlockBehaviour.Properties.of()
                        .mapColor(MapColor.GOLD)
                        .strength(8F, 800F)
                        .requiresCorrectToolForDrops()
                        .lightLevel(bs -> bs.getValue(FIREFLY_COUNT) * 3));
        XDI8AHO_PORTAL_BLOCK = block("xdi8aho_portal", Xdi8ahoPortalBlock::new,
                BlockBehaviour.Properties.of()
                        .mapColor(MapColor.GOLD)
                        .lightLevel(s -> 11)
                        .strength(-1)
                        .noCollission()
                        .sound(SoundType.AMETHYST));
        XDI8AHO_BACK_PORTAL_CORE_BLOCK = block("xdi8aho_back_portal_core", BackPortalCoreBlock::new,
                BlockBehaviour.Properties.of()
                        .mapColor(MapColor.COLOR_LIGHT_GRAY)
                        .strength(10F, 800F)
                        .requiresCorrectToolForDrops());
        XDI8AHO_BACK_FIRE_BLOCK = block("xdi8aho_back_portal_fire", BackPortalFireBlock::new,
                BlockBehaviour.Properties.of()
                        .mapColor(MapColor.FIRE)
                        .replaceable()
                        .instabreak()
                        .noCollission()
                        .sound(SoundType.WOOL)
                        .lightLevel((bs) -> 15));
        XDI8_TABLE = block("xdi8_table", Xdi8TableBlock::new,
                BlockBehaviour.Properties.of()
                        .requiresCorrectToolForDrops()
                        .strength(3.5F, 6.0F));
        SymbolStoneBlock.registerAll(RegistryHelper::block);
        DARK_SYMBOL_STONE = defaultBlock("dark_symbol_stone",
                BlockBehaviour.Properties.of()
                        .strength(2.0F, 8.0F)
                        .requiresCorrectToolForDrops()
                        .sound(SoundType.POLISHED_DEEPSLATE));
        SYMBOL_STONE_BRICKS = defaultBlock("symbol_stone_bricks",
                BlockBehaviour.Properties.of()
                        .strength(1.0F, 4.0F)
                        .requiresCorrectToolForDrops()
                        .sound(SoundType.STONE));
        CEDAR_BUTTON = block("cedar_button",
                (properties) -> new ButtonBlock(redwoodSet, 30, properties),
                BlockBehaviour.Properties.of()
                        .noCollission()
                        .strength(0.5F)
                        .pushReaction(PushReaction.DESTROY)
                        .sound(SoundType.WOOD));
        CEDAR_DOOR = block("cedar_door",
                (properties) -> new DoorBlock(redwoodSet, properties),
                woodenBlock().noCollission().strength(3.0F).pushReaction(PushReaction.DESTROY));
        CEDAR_FENCE = block("cedar_fence", FenceBlock::new,
                woodenBlock().forceSolidOn().strength(2.0F, 3.0F));
        CEDAR_FENCE_GATE = block("cedar_fence_gate",
                (properties) -> new FenceGateBlock(redwoodType, properties),
                woodenBlock().forceSolidOn().strength(2.0F, 3.0F));
        CEDAR_LEAVES = createLeaves("cedar_leaves");
        CEDAR_LOG = block("cedar_log", RotatedPillarBlock::new,
                woodenBlock().mapColor((blockState) -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ?
                                redwoodColor : MapColor.PODZOL)
                        .strength(2.0F));
        STRIPPED_CEDAR_LOG = block("stripped_cedar_log", RotatedPillarBlock::new,
                woodenBlock().strength(2.0F));
        CEDAR_PLANKS = defaultBlock("cedar_planks",
                woodenBlock().strength(2.0F, 3.0F));
        CEDAR_PRESSURE_PLATE = block("cedar_pressure_plate",
                (properties) -> new PressurePlateBlock(redwoodSet, properties),
                woodenBlock().forceSolidOn().noCollission().strength(0.5f).pushReaction(PushReaction.DESTROY));
        CEDAR_SAPLING = block("cedar_sapling",
                (properties) -> new SaplingBlock(FireflyTreeFeatures.CEDAR_TREE_GROWER.get(), properties),
                BlockBehaviour.Properties.of()
                        .mapColor(MapColor.PLANT)
                        .instabreak()
                        .noCollission()
                        .randomTicks()
                        .pushReaction(PushReaction.DESTROY)
                        .sound(SoundType.GRASS));
        CEDAR_SIGN = block("cedar_sign",
                (properties) -> new StandingSignBlock(redwoodType, properties),
                woodenBlock().forceSolidOn().noCollission().strength(1.0F));
        CEDAR_SLAB = block("cedar_slab", SlabBlock::new,
                woodenBlock().strength(2.0F, 3.0F));
        CEDAR_STAIRS = block("cedar_stairs",
                (properties) -> new StairBlock(CEDAR_PLANKS.get().defaultBlockState(), properties),
                woodenBlock().strength(2.0F, 3.0F));
        CEDAR_TRAPDOOR = block("cedar_trapdoor",
                (properties) -> new TrapDoorBlock(redwoodSet, properties),
                woodenBlock().noOcclusion().strength(3.0F).isValidSpawn(FireflyBlocks::never));
        CEDAR_WALL_SIGN = block("cedar_wall_sign",
                (properties) -> new WallSignBlock(redwoodType, properties),
                woodenBlock().overrideLootTable(Optional.of(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocationTool.create("firefly8:block/cedar_sign"))))
                        .overrideDescription("block.firefly8.cedar_sign").forceSolidOn().noCollission().strength(1.0F));
        CEDAR_WOOD = block("cedar_wood", RotatedPillarBlock::new,
                woodenBlock().strength(2.0F));
        STRIPPED_CEDAR_WOOD = block("stripped_cedar_wood", RotatedPillarBlock::new,
                woodenBlock().strength(2.0F));
        SYMBOL_STONE_NN = block("symbol_stone_nn", SymbolStoneNNBlock::new,
                BlockBehaviour.Properties.of()
                        .overrideDescription("block.firefly8.symbol_stone")
                        .mapColor(MapColor.COLOR_LIGHT_GRAY)
                        .strength(1.5F, 8.0F)
                        .requiresCorrectToolForDrops());
    }


    private static boolean never(BlockState state, BlockGetter getter, BlockPos pos, EntityType<?> entity) {
        return false;
    }
}
