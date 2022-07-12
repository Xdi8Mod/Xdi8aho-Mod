package top.xdi8.mod.firefly8.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import top.xdi8.mod.firefly8.world.Xdi8DeathHook;

@Mixin(Minecraft.class)
public class MixinDeathSetScreenC {
    @Redirect(
            method = "setScreen",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/player/LocalPlayer;isDeadOrDying()Z"
            )
    )
    private boolean modifyIsDeathOfDying(LocalPlayer instance) {
        if (instance.isDeadOrDying()) {
            if (new ResourceLocation("firefly8:xdi8aho").equals(
                    instance.getLevel().dimension().location())) {
                return Xdi8DeathHook.modifyIsDeathOfDying(instance);
            }
            return true;
        }
        return false;
    }
}
