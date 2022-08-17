package top.xdi8.mod.firefly8.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.featurehouse.mcmod.spm.platform.api.reg.PlatformRegister;
import top.xdi8.mod.firefly8.block.symbol.SymbolStoneBlock;
import top.xdi8.mod.firefly8.block.symbol.SymbolStoneNNBlock;
import top.xdi8.mod.firefly8.block.symbol.Xdi8TableBlock;
import top.xdi8.mod.firefly8.util.InternalRegistryLogWrapper;

import java.util.function.Supplier;

public class FireflyBlocks {
    public static final InternalRegistryLogWrapper LOG_WRAPPER = InternalRegistryLogWrapper.firefly8("blocks");

    public static final Supplier<Block> XDI8AHO_PORTAL_CORE_BLOCK,
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
            SYMBOL_STONE_NN;

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
        SYMBOL_STONE_NN = reg.block("symbol_stone_nn", SymbolStoneNNBlock::new);
    }

    private static Supplier<Block> ofDefaultBlock(String id,
                                                        Supplier<BlockBehaviour.Properties> propSup) {
        return reg.block(id, () -> new Block(propSup.get()));
    }
}
