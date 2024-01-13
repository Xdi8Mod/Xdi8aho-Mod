package top.xdi8.mod.firefly8.block;

import dev.architectury.registry.registries.RegistrySupplier;
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
import org.featurehouse.mcmod.spm.platform.api.reg.PlatformRegister;
import top.xdi8.mod.firefly8.block.cedar.CedarTreeGrower;
import top.xdi8.mod.firefly8.block.symbol.SymbolStoneBlock;
import top.xdi8.mod.firefly8.block.symbol.SymbolStoneNNBlock;
import top.xdi8.mod.firefly8.block.symbol.Xdi8TableBlock;
import top.xdi8.mod.firefly8.util.InternalRegistryLogWrapper;

import java.util.function.Supplier;

public class FireflyBlocks {
    public static final InternalRegistryLogWrapper LOG_WRAPPER = InternalRegistryLogWrapper.firefly8("blocks");

    public static final MaterialColor cedarColor = MaterialColor.COLOR_RED;
    public static final WoodType cedarWood = new WoodType("cedar");

    // From net.minecraft.world.level.block.Blocks
    private static boolean never(BlockState state, BlockGetter getter, BlockPos pos) {
        return false;
    }

    private static boolean never(BlockState state, BlockGetter getter, BlockPos pos, EntityType<?> entity) {
        return false;
    }

    private static Boolean ocelotOrParrot(BlockState state, BlockGetter getter, BlockPos pos, EntityType<?> entityType) {
        return entityType == EntityType.OCELOT || entityType == EntityType.PARROT;
    }

    public static final RegistrySupplier<Block> XDI8AHO_PORTAL_CORE_BLOCK,
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
            STRIPPED_CEDAR_WOOD,
            CEDAR_WALL_SIGN;

    private static final PlatformRegister reg;

    static {
        reg = PlatformRegister.of("firefly8");
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
        XDI8AHO_PORTAL_TOP_BLOCK = reg.block("xdi8aho_torch_top", Xdi8ahoPortalTopBlock::new);
        XDI8AHO_PORTAL_BLOCK = reg.block("xdi8aho_portal", Xdi8ahoPortalBlock::new);
        XDI8AHO_BACK_PORTAL_CORE_BLOCK = reg.block("xdi8aho_back_portal_core", BackPortalCoreBlock::new);
        XDI8AHO_BACK_FIRE_BLOCK = reg.block("xdi8aho_back_portal_fire", BackPortalFireBlock::new);
        XDI8_TABLE = reg.block("xdi8_table", Xdi8TableBlock::new);
        SymbolStoneBlock.registerAll((id, sup) -> reg.block(id, sup::get));
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
        CEDAR_BUTTON = reg.block("cedar_button", () ->
                new WoodButtonBlock(BlockBehaviour.Properties.of(Material.DECORATION, cedarColor)
                        .noCollission()
                        .strength(0.5f)
                        .sound(SoundType.WOOD)));
        CEDAR_DOOR = reg.block("cedar_door", () ->
                new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD, cedarColor)
                        .strength(3.0F)
                        .sound(SoundType.WOOD)
                        .noOcclusion()));
        CEDAR_FENCE = reg.block("cedar_fence", () ->
                new FenceBlock(BlockBehaviour.Properties.of(Material.WOOD, cedarColor)
                        .strength(2.0f, 3.0f)
                        .sound(SoundType.WOOD)));
        CEDAR_FENCE_GATE = reg.block("cedar_fence_gate", () ->
                new FenceGateBlock(BlockBehaviour.Properties.of(Material.WOOD, cedarColor)
                        .strength(2.0f, 3.0f)
                        .sound(SoundType.WOOD)));
        CEDAR_LEAVES = reg.block("cedar_leaves", () ->
                new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES)
                        .strength(0.2F)
                        .randomTicks()
                        .sound(SoundType.GRASS).noOcclusion()
                        .isValidSpawn(FireflyBlocks::ocelotOrParrot)
                        .isSuffocating(FireflyBlocks::never)
                        .isViewBlocking(FireflyBlocks::never)));
        CEDAR_LOG = reg.block("cedar_log", () ->
                new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, (state) ->
                                state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? cedarColor : MaterialColor.PODZOL)
                        .strength(2.0F)
                        .sound(SoundType.WOOD)));
        STRIPPED_CEDAR_LOG = reg.block("stripped_cedar_log", () ->
                new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, cedarColor)
                        .strength(2.0F)
                        .sound(SoundType.WOOD)));
        CEDAR_PLANKS = ofDefaultBlock("cedar_planks", () ->
                BlockBehaviour.Properties.of(Material.WOOD, cedarColor)
                        .strength(2.0F, 3.0F)
                        .sound(SoundType.WOOD));
        CEDAR_PRESSURE_PLATE = reg.block("cedar_pressure_plate", () ->
                new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING,
                        BlockBehaviour.Properties.of(Material.WOOD, cedarColor)
                                .noCollission()
                                .strength(0.5f)
                                .sound(SoundType.WOOD)));
        CEDAR_SAPLING = reg.block("cedar_sapling", () ->
                new SaplingBlock(new CedarTreeGrower(), BlockBehaviour.Properties.of(Material.PLANT)
                        .noCollission()
                        .randomTicks()
                        .instabreak()
                        .sound(SoundType.GRASS)));
        CEDAR_SIGN = reg.block("cedar_sign", () ->
                new StandingSignBlock(BlockBehaviour.Properties.of(Material.WOOD, cedarColor)
                        .noCollission()
                        .strength(1.0F)
                        .sound(SoundType.WOOD), cedarWood));
        CEDAR_SLAB = reg.block("cedar_slab", () ->
                new SlabBlock(BlockBehaviour.Properties.of(Material.WOOD, cedarColor)
                        .strength(2.0f, 3.0f)
                        .sound(SoundType.WOOD)));
        CEDAR_STAIRS = reg.block("cedar_stairs", () ->
                new StairBlock(CEDAR_PLANKS.isPresent() ? CEDAR_PLANKS.get().defaultBlockState() : new Block(BlockBehaviour.Properties.of(Material.WOOD, cedarColor)
                        .strength(2.0F, 3.0F)
                        .sound(SoundType.WOOD)).defaultBlockState(),
                        BlockBehaviour.Properties.of(Material.WOOD, cedarColor)
                        .strength(2.0F, 3.0F)
                        .sound(SoundType.WOOD)));
        CEDAR_TRAPDOOR = reg.block("cedar_trapdoor", () ->
                new TrapDoorBlock(BlockBehaviour.Properties.of(Material.WOOD, cedarColor)
                        .strength(3.0F)
                        .sound(SoundType.WOOD)
                        .noOcclusion()
                        .isValidSpawn(FireflyBlocks::never)));
        CEDAR_WALL_SIGN = reg.block("cedar_wall_sign", () ->
                new WallSignBlock(BlockBehaviour.Properties.of(Material.WOOD)
                        .noCollission()
                        .strength(1.0F)
                        .sound(SoundType.WOOD)
                        .dropsLike(CEDAR_SIGN.get()), cedarWood));
        CEDAR_WOOD = reg.block("cedar_wood", () ->
                new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, cedarColor)
                        .strength(2.0f)
                        .sound(SoundType.WOOD)));
        STRIPPED_CEDAR_WOOD = reg.block("stripped_cedar_wood", () ->
                new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, cedarColor)
                        .strength(2.0f)
                        .sound(SoundType.WOOD)));
        SYMBOL_STONE_NN = reg.block("symbol_stone_nn", SymbolStoneNNBlock::new);
    }

    private static RegistrySupplier<Block> ofDefaultBlock(String id,
                                                          Supplier<BlockBehaviour.Properties> propSup) {
        return reg.block(id, () -> new Block(propSup.get()));
    }
}
