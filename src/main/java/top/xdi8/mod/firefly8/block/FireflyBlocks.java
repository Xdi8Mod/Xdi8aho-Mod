package top.xdi8.mod.firefly8.block;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FireflyBlocks {
    public static final DeferredRegister<Block> REGISTRY
            = DeferredRegister.create(ForgeRegistries.BLOCKS, "firefly8");

    public static final RegistryObject<Block> XDI8AHO_PORTAL_CORE_BLOCK;

    static {
        XDI8AHO_PORTAL_CORE_BLOCK = REGISTRY.register("xdi8aho_portal_core", Xdi8ahoPortalCoreBlock::new);
    }
}
