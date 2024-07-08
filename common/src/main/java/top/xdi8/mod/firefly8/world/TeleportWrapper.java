package top.xdi8.mod.firefly8.world;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.PortalInfo;

public interface TeleportWrapper {
    PortalInfo getPortalInfo(Entity oldEntity, ServerLevel destWorld);
}
