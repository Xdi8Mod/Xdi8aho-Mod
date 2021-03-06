package top.xdi8.mod.firefly8.block.entity;

import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import top.xdi8.mod.firefly8.block.FireflyBlocks;

public final class FireflyBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, "firefly8");

    public static final RegistryObject<BlockEntityType<PortalTopBlockEntity>> PORTAL_TOP;
    public static final RegistryObject<BlockEntityType<BackPortalCoreBlockEntity>> BACK_PORTAL_CORE;

    static {
        PORTAL_TOP = REGISTRY.register("portal_top", () ->
                BlockEntityType.Builder.of(PortalTopBlockEntity::new, FireflyBlocks.XDI8AHO_PORTAL_TOP_BLOCK.get())
                        .build(getType("portal_top")));
        BACK_PORTAL_CORE = REGISTRY.register("back_portal_core", () ->
                BlockEntityType.Builder.of(BackPortalCoreBlockEntity::new, FireflyBlocks.XDI8AHO_BACK_PORTAL_CORE_BLOCK.get())
                        .build(getType("back_portal_core")));
    }

    private static com.mojang.datafixers.types.Type<?> getType(String id) {
        var type = Util.fetchChoiceType(References.BLOCK_ENTITY, new ResourceLocation("firefly8", id).toString());
        assert type != null;
        return type;
    }
}
