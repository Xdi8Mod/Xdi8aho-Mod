package top.xdi8.mod.firefly8.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import org.featurehouse.mcmod.spm.platform.api.reg.PlatformRegister;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import top.xdi8.mod.firefly8.util.InternalRegistryLogWrapper;

import java.util.Collections;
import java.util.function.Supplier;

public final class FireflyBlockEntityTypes {
    public static final InternalRegistryLogWrapper LOG_WRAPPER = InternalRegistryLogWrapper.firefly8("block_entities");

    public static final Supplier<BlockEntityType<PortalTopBlockEntity>> PORTAL_TOP;
    public static final Supplier<BlockEntityType<BackPortalCoreBlockEntity>> BACK_PORTAL_CORE;

    static {
        var reg = PlatformRegister.of("firefly8");
        PORTAL_TOP = reg.blockEntity("portal_top", PortalTopBlockEntity::new,
                Collections.singleton(FireflyBlocks.XDI8AHO_PORTAL_TOP_BLOCK));
        BACK_PORTAL_CORE = reg.blockEntity("back_portal_core", BackPortalCoreBlockEntity::new,
                Collections.singleton(FireflyBlocks.XDI8AHO_BACK_PORTAL_CORE_BLOCK));
    }
}
