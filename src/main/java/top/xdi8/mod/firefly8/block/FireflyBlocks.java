package top.xdi8.mod.firefly8.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import top.xdi8.mod.firefly8.block.symbol.SymbolStoneBlock;
import top.xdi8.mod.firefly8.block.symbol.Xdi8TableBlock;

import java.util.function.Supplier;

public class FireflyBlocks {
    public static final DeferredRegister<Block> REGISTRY
            = DeferredRegister.create(ForgeRegistries.BLOCKS, "firefly8");

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
            XDI8_TABLE;

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
    }

    private static RegistryObject<Block> ofDefaultBlock(String id,
                                                        Supplier<BlockBehaviour.Properties> propSup) {
        return REGISTRY.register(id, () -> new Block(propSup.get()));
    }
}
