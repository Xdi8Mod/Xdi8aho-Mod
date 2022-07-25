package top.xdi8.mod.firefly8.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

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
            DEEPSLATE_INDIUM_ORE_BLOCK;

    static {
        INDIUM_BLOCK = REGISTRY.register("indium_block", IndiumBlock::new);
        INDIUM_ORE_BLOCK = REGISTRY.register("indium_ore", IndiumOreBlock::new);
        DEEPSLATE_INDIUM_ORE_BLOCK = REGISTRY.register("deepslate_indium_ore", DeepslateIndiumOreBlock::new);
        XDI8AHO_PORTAL_CORE_BLOCK = REGISTRY.register("xdi8aho_portal_core", Xdi8ahoPortalCoreBlock::new);
        XDI8AHO_PORTAL_TOP_BLOCK = REGISTRY.register("xdi8aho_torch_top", Xdi8ahoPortalTopBlock::new);
        XDI8AHO_PORTAL_BLOCK = REGISTRY.register("xdi8aho_portal", Xdi8ahoPortalBlock::new);
        XDI8AHO_BACK_PORTAL_CORE_BLOCK = REGISTRY.register("xdi8aho_back_portal_core",
                () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                        .strength(30F, 1200F)
                        .requiresCorrectToolForDrops()));
        XDI8AHO_BACK_FIRE_BLOCK = REGISTRY.register("xdi8aho_back_portal_fire", BackPortalFireBlock::new);
    }
}
