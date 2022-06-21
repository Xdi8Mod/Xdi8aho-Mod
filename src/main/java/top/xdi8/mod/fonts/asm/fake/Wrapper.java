package top.xdi8.mod.fonts.asm.fake;

import net.minecraft.client.gui.font.providers.GlyphProviderBuilderType;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = {GlyphProviderBuilderType.class})
class Wrapper {}
