package top.xdi8.mod.firefly8.block.symbol;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.xdi8.mod.firefly8.core.letters.KeyedLetter;
import top.xdi8.mod.firefly8.core.letters.LettersUtil;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class SymbolStoneBlock extends Block implements KeyedLetter.Provider {
    private final KeyedLetter letter;

    public SymbolStoneBlock(KeyedLetter letter) {
        super(Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_GRAY)
                .requiresCorrectToolForDrops()
                .strength(1.5F, 8.0F)
        );
        this.letter = letter;
    }

    @Override
    public @NotNull String getDescriptionId() {
        return "block.firefly8.symbol_stone";
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable BlockGetter pLevel,
                                @NotNull List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        if (letter.isNull()) return;
        List<String> sb = new ArrayList<>();
        if (letter.hasUppercase()) sb.add(Character.toString(letter.uppercase()));
        if (letter.hasMiddleCase()) sb.add(Character.toString(letter.middleCase()));
        if (letter.hasLowercase()) sb.add(Character.toString(letter.lowercase()));
        String s = join(sb.iterator(), " ");
        if (!s.isBlank()) {
            pTooltip.add(Component.translatable("block.firefly8.symbol_stone.letter", s));
        }
    }

    public SymbolStoneBlock withLetter(KeyedLetter letter) {
        return fromLetter(letter);
    }

    @NotNull
    @Override
    public KeyedLetter letter() {
        return letter;
    }

    @ApiStatus.Internal
    public static void registerAll(BiConsumer<String /*id*/, Supplier<? extends Block>> registry) {
        registry.accept("symbol_stone", () -> {
            var block = new SymbolStoneBlock(KeyedLetter.empty());
            LETTER_TO_BLOCK.put(KeyedLetter.empty(), block);
            return block;
        });
        LettersUtil.forEach((key, letter) -> {
            if (letter.isNull()) return;
            Supplier<Block> sup = () -> {
                var block = new SymbolStoneBlock(letter);
                LETTER_TO_BLOCK.put(letter, block);
                return block;
            };
            if ("firefly8".equals(key.getNamespace())) {
                registry.accept("symbol_stone_" + key.getPath(), sup);
            } else {
                registry.accept("symbol_stone_" + key.getNamespace() +
                        "__" + key.getPath(), sup);
            }
        });
    }

    static final Map<KeyedLetter, SymbolStoneBlock> LETTER_TO_BLOCK = new HashMap<>();
    public static SymbolStoneBlock fromLetter(@Nullable KeyedLetter letter) {
        final SymbolStoneBlock block = LETTER_TO_BLOCK.get(letter);
        if (block == null) return LETTER_TO_BLOCK.get(KeyedLetter.empty());
        return block;
    }

    // from plexus-utils
    private static String join(Iterator<?> iterator, String separator ) {
        if ( separator == null ) {
            separator = "";
        }
        StringBuilder buf = new StringBuilder( 256 ); // Java default is 16, probably too small
        while ( iterator.hasNext() ) {
            buf.append( iterator.next() );
            if ( iterator.hasNext() ) {
                buf.append( separator );
            }
        }
        return buf.toString();
    }
}
