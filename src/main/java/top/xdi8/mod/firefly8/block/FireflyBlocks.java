package top.xdi8.mod.firefly8.block;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FireflyBlocks {
    public static final DeferredRegister<Block> REGISTRY
            = DeferredRegister.create(ForgeRegistries.BLOCKS, "firefly8");

    public static final RegistryObject<Block> XDI8AHO_PORTAL_CORE_BLOCK,
            XDI8AHO_PORTAL_TOP_BLOCK,
            INDIUM_BLOCK,
            INDIUM_ORE_BLOCK,
            DEEPSLATE_INDIUM_ORE_BLOCK;

    static {
        XDI8AHO_PORTAL_CORE_BLOCK = REGISTRY.register("xdi8aho_portal_core", Xdi8ahoPortalCoreBlock::new);
        XDI8AHO_PORTAL_TOP_BLOCK = REGISTRY.register("xdi8aho_torch_top", Xdi8ahoPortalTopBlock::new);
        INDIUM_BLOCK = REGISTRY.register("indium_block", IndiumBlock::new);
        INDIUM_ORE_BLOCK = REGISTRY.register("indium_ore", IndiumOreBlock::new);
        DEEPSLATE_INDIUM_ORE_BLOCK = REGISTRY.register("deepslate_indium_ore", DeepslateIndiumOreBlock::new);
    }
}
