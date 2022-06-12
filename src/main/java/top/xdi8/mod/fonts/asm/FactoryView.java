package top.xdi8.mod.fonts.asm;

import com.google.gson.JsonObject;
import net.minecraft.client.gui.font.providers.GlyphProviderBuilder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.fonts.TrueTypeExtendedGlyphProviderBuilder;

import java.util.function.Function;

/**
 * @deprecated this is only used by
 * {@link HackGlyphProviderBuildType ASM code
 * generation}.
 */
@SuppressWarnings("unused")
@Deprecated
public interface FactoryView {
     Function<JsonObject, GlyphProviderBuilder> FACTORY
             = TrueTypeExtendedGlyphProviderBuilder::fromJson;
}
