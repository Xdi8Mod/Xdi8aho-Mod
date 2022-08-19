package top.xdi8.mod.firefly8.world.fabric;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.PortalInfo;
import top.xdi8.mod.firefly8.ext.IPortalCooldownEntity;
import top.xdi8.mod.firefly8.world.TeleportWrapper;

public class Xdi8DimensionUtilsImpl {
    public static Entity changeDimension(Entity e, ServerLevel xdi8Level, TeleportWrapper teleporter) {
        final PortalInfo portalInfo = teleporter.getPortalInfo(e, xdi8Level);
        if (portalInfo == null) return null;
        final Entity teleport = FabricDimensions.teleport(e, xdi8Level, portalInfo);
        if (teleport != null) IPortalCooldownEntity.xdi8$extend(teleport).xdi8$resetCooldown();
        return teleport;
    }
}
