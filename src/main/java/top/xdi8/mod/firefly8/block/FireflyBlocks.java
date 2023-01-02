package top.xdi8.mod.firefly8.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import top.xdi8.mod.firefly8.block.symbol.SymbolStoneBlock;
import top.xdi8.mod.firefly8.block.symbol.SymbolStoneNNBlock;
import top.xdi8.mod.firefly8.block.symbol.Xdi8TableBlock;

import java.util.function.Supplier;

public class FireflyBlocks {
    public static final DeferredRegister<Block> REGISTRY
            = DeferredRegister.create(ForgeRegistries.BLOCKS, "firefly8");

    public static final MaterialColor cedarColor = MaterialColor.COLOR_RED;
    public static final WoodType cedarWood = new WoodType("cedar");

    // From net.minecraft.world.level.block.Blocks
    private static boolean never(BlockState state, BlockGetter getter, BlockPos pos) {
        return false;
    }

    private static Boolean ocelotOrParrot(BlockState state, BlockGetter getter, BlockPos pos, EntityType<?> entityType) {
        return entityType == EntityType.OCELOT || entityType == EntityType.PARROT;
    }

    public static final RegistryObject<Block> XDI8AHO_PORTAL_CORE_BLOCK,
            XDI8AHO_PORTAL_TOP_BLOCK,
            XDI8AHO_PORTAL_BLOCK,
            XDI8AHO_BACK_PORTAL_CORE_BLOCK,
            XDI8AHO_BACK_FIRE_BLOCK,
            INDIUM_BLOCK,
            INDIUM_ORE_BLOCK,
            DEEPSLATE_INDIUM_ORE_BLOCK,
            DARK_SYMBOL_STONE,
            SYMBOL_STONE_BRICKS,
            XDI8_TABLE,
            SYMBOL_STONE_NN,
            CEDAR_PLANKS,
            CEDAR_DOOR,
            CEDAR_LEAVES,
            CEDAR_LOG,
            STRIPPED_CEDAR_LOG,
            CEDAR_SIGN,
            CEDAR_WALL_SIGN;

    static {
        INDIUM_BLOCK = ofDefaultBlock("indium_block", () ->
                BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_GRAY)
                        .strength(1.0F, 6.0F)
                        .requiresCorrectToolForDrops()
        );
        INDIUM_ORE_BLOCK = ofDefaultBlock("indium_ore", () ->
                BlockBehaviour.Properties.of(Material.STONE)
                        .strength(3.0F, 3.0F)
                        .requiresCorrectToolForDrops()
        );
        DEEPSLATE_INDIUM_ORE_BLOCK = ofDefaultBlock("deepslate_indium_ore", () ->
                BlockBehaviour.Properties.of(Material.STONE, MaterialColor.DEEPSLATE)
                        .strength(4.5F, 3.0F)
                        .requiresCorrectToolForDrops()
                        .sound(SoundType.DEEPSLATE)
        );
        XDI8AHO_PORTAL_CORE_BLOCK = ofDefaultBlock("xdi8aho_portal_core", () ->
                BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK)
                        .strength(10F, 1200F)
                        .requiresCorrectToolForDrops()
        );
        XDI8AHO_PORTAL_TOP_BLOCK = REGISTRY.register("xdi8aho_torch_top", Xdi8ahoPortalTopBlock::new);
        XDI8AHO_PORTAL_BLOCK = REGISTRY.register("xdi8aho_portal", Xdi8ahoPortalBlock::new);
        XDI8AHO_BACK_PORTAL_CORE_BLOCK = REGISTRY.register("xdi8aho_back_portal_core", BackPortalCoreBlock::new);
        XDI8AHO_BACK_FIRE_BLOCK = REGISTRY.register("xdi8aho_back_portal_fire", BackPortalFireBlock::new);
        XDI8_TABLE = REGISTRY.register("xdi8_table", Xdi8TableBlock::new);
        SymbolStoneBlock.registerAll(REGISTRY::register);
        DARK_SYMBOL_STONE = ofDefaultBlock("dark_symbol_stone", () ->
                BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_GRAY)
                        .strength(2.0F, 8.0F)
                        .requiresCorrectToolForDrops()
                        .sound(SoundType.POLISHED_DEEPSLATE)
        );
        SYMBOL_STONE_BRICKS = ofDefaultBlock("symbol_stone_bricks", () ->
                BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_GRAY)
                        .strength(1.0F, 4.0F)
                        .requiresCorrectToolForDrops()
                        .sound(SoundType.STONE)
        );
        SYMBOL_STONE_NN = REGISTRY.register("symbol_stone_nn", SymbolStoneNNBlock::new);
        CEDAR_PLANKS = ofDefaultBlock("cedar_planks", () ->
                BlockBehaviour.Properties.of(Material.WOOD, cedarColor)
                        .strength(2.0F, 3.0F)
                        .sound(SoundType.WOOD));
        CEDAR_DOOR = REGISTRY.register("cedar_door", () ->
                new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD, cedarColor)
                        .strength(3.0F)
                        .sound(SoundType.WOOD)
                        .noOcclusion()));
        CEDAR_LEAVES = REGISTRY.register("cedar_leaves", () ->
                new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES)
                        .strength(0.2F)
                        .randomTicks()
                        .sound(SoundType.GRASS).noOcclusion()
                        .isValidSpawn(FireflyBlocks::ocelotOrParrot)
                        .isSuffocating(FireflyBlocks::never)
                        .isViewBlocking(FireflyBlocks::never)));
        CEDAR_LOG = REGISTRY.register("cedar_log", () ->
                new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, (state) ->
                        state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? cedarColor : MaterialColor.PODZOL)
                        .strength(2.0F)
                        .sound(SoundType.WOOD)));
        STRIPPED_CEDAR_LOG = REGISTRY.register("stripped_cedar_log", () ->
                new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, cedarColor)
                        .strength(2.0F)
                        .sound(SoundType.WOOD)));
        CEDAR_SIGN = REGISTRY.register("cedar_sign", () ->
                new StandingSignBlock(BlockBehaviour.Properties.of(Material.WOOD, cedarColor)
                        .noCollission()
                        .strength(1.0F)
                        .sound(SoundType.WOOD), cedarWood));
        CEDAR_WALL_SIGN = REGISTRY.register("cedar_wall_sign", () ->
                new WallSignBlock(BlockBehaviour.Properties.of(Material.WOOD)
                        .noCollission()
                        .strength(1.0F)
                        .sound(SoundType.WOOD)
                        .lootFrom(CEDAR_SIGN), cedarWood));
    }

    private static RegistryObject<Block> ofDefaultBlock(String id,
                                                        Supplier<BlockBehaviour.Properties> propSup) {
        return REGISTRY.register(id, () -> new Block(propSup.get()));
    }
}
