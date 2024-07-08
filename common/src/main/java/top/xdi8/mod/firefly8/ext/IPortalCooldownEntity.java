package top.xdi8.mod.firefly8.ext;

import net.minecraft.world.entity.Entity;

public interface IPortalCooldownEntity {
    static IPortalCooldownEntity xdi8$extend(Entity entity) { return (IPortalCooldownEntity) entity; }

    void xdi8$processCooldown();
    boolean xdi8$isOnCooldown();
    void xdi8$resetCooldown();
    void xdi8$resetShortCooldown();
}
