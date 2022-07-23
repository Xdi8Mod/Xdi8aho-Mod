package top.xdi8.mod.fonts.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.xdi8.mod.fonts.Xdi8FontsMod;

import java.awt.*;
import java.util.List;
import java.util.Set;

@Mixin(targets = "icyllis.modernui.forge.ModernUIForge")
public class MixinLoadFonts {
    @Inject(at = @At("HEAD"), method = "loadFonts(Ljava/util/List;Ljava/util/Set;)V", remap = false)
    private static void onLoadFonts(List<? extends String> configs, Set<Font> selected, CallbackInfo ci) {
        Xdi8FontsMod.loadFont(selected);
    }
}
