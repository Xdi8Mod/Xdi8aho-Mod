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
public interface FactoryView {
     @Contract(pure = true)
     static @NotNull Function<JsonObject, GlyphProviderBuilder> getFactory() {
          return TrueTypeExtendedGlyphProviderBuilder::fromJson;
     }
}
