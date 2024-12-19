package top.xdi8.mod.firefly8.block.entity;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.level.block.entity.BlockEntityType;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import io.github.qwerty770.mcmod.xdi8.api.InternalRegistryLogWrapper;

import static io.github.qwerty770.mcmod.xdi8.util.registries.RegistryHelper.blockEntity;

public final class FireflyBlockEntityTypes {
    public static final InternalRegistryLogWrapper LOG_WRAPPER = InternalRegistryLogWrapper.firefly8("block_entities");

    public static final RegistrySupplier<BlockEntityType<PortalTopBlockEntity>> PORTAL_TOP =
            blockEntity("portal_top", PortalTopBlockEntity::new, FireflyBlocks.XDI8AHO_PORTAL_TOP_BLOCK);
    public static final RegistrySupplier<BlockEntityType<BackPortalCoreBlockEntity>> BACK_PORTAL_CORE =
            blockEntity("back_portal_core", BackPortalCoreBlockEntity::new, FireflyBlocks.XDI8AHO_BACK_PORTAL_CORE_BLOCK);
}
