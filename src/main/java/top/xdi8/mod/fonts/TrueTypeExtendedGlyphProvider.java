package top.xdi8.mod.fonts;

import com.mojang.blaze3d.font.TrueTypeGlyphProvider;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.stb.STBTTFontinfo;

import java.nio.ByteBuffer;
import java.util.stream.IntStream;

@OnlyIn(Dist.CLIENT)
public class TrueTypeExtendedGlyphProvider extends TrueTypeGlyphProvider {
    public TrueTypeExtendedGlyphProvider(ByteBuffer pFontMemory, STBTTFontinfo pFont, float pHeight, float pOversample, float pShiftX, float pShiftY, String pSkip) {
        super(pFontMemory, pFont, pHeight, pOversample, pShiftX, pShiftY, pSkip);
    }

    @Override
    public @NotNull IntSet getSupportedGlyphs() {
        return IntStream.range(0, 0x110000).filter(codePoint ->
                !this.skip.contains(codePoint))
                .collect(IntOpenHashSet::new, IntCollection::add, IntCollection::addAll);
    }
}
