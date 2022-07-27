package top.xdi8.mod.fonts.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@Mixin(targets = "icyllis.modernui.forge.ModernUIForge$Client")
public class MixinLoadFonts {
    // Wrapper
    // Real things are in LoadFontsConfigWrapper
}
