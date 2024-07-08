package top.xdi8.mod.firefly8.mixin;

import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.xdi8.mod.firefly8.ext.IPortalCooldownEntity;
import top.xdi8.mod.firefly8.world.Xdi8TeleporterImpl;

@Mixin(Entity.class)
public class MixinEntityBaseTick implements IPortalCooldownEntity {
    @Inject(at = @At("RETURN"), method = "baseTick()V")
    private void onBaseTick(CallbackInfo ci) {
        xdi8$processCooldown();
    }

    private int xdi8$portalCooldown;

    @Override
    public void xdi8$processCooldown() {
        if (xdi8$isOnCooldown())
            xdi8$portalCooldown--;
    }

    @Override
    public boolean xdi8$isOnCooldown() {
        return xdi8$portalCooldown > 0;
    }

    @Override
    public void xdi8$resetCooldown() {
        xdi8$portalCooldown = Xdi8TeleporterImpl.COOLDOWN;
    }

    @Override
    public void xdi8$resetShortCooldown() {
        xdi8$portalCooldown = Math.max(xdi8$portalCooldown, Xdi8TeleporterImpl.COOLDOWN_SHORT);
    }
}
