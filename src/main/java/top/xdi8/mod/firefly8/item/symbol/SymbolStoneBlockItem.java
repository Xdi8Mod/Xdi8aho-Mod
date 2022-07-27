package top.xdi8.mod.firefly8.item.symbol;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.xdi8.mod.firefly8.block.symbol.SymbolStoneBlock;
import top.xdi8.mod.firefly8.core.letters.KeyedLetter;
import top.xdi8.mod.firefly8.core.letters.LettersUtil;
import top.xdi8.mod.firefly8.item.FireflyItems;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class SymbolStoneBlockItem extends BlockItem implements KeyedLetter.Provider {
    private final KeyedLetter letter;

    SymbolStoneBlockItem(KeyedLetter letter) {
        this(letter, new Properties()
                .rarity(Rarity.UNCOMMON)
                .tab(FireflyItems.TAB)
        );
    }

    public SymbolStoneBlockItem(KeyedLetter letter, Properties pProperties) {
        super(SymbolStoneBlock.fromLetter(letter), pProperties);
        this.letter = letter;
    }

    public SymbolStoneBlockItem withLetter(KeyedLetter letter) {
        return fromLetter(letter);
    }

    @NotNull
    @Override
    public KeyedLetter letter() {
        return letter;
    }

    public static void registerAll(BiConsumer<String, Supplier<? extends Item>> registry) {
        registry.accept("symbol_stone", () -> {
            var item = new SymbolStoneBlockItem(KeyedLetter.empty(), new Properties().tab(FireflyItems.TAB));
            LETTER_TO_ITEM.put(KeyedLetter.empty(), item);
            return item;
        });
        LettersUtil.forEach((key, letter) -> {
            if (letter.isNull()) return;
            Supplier<Item> sup = () -> {
                var item = new SymbolStoneBlockItem(letter);
                LETTER_TO_ITEM.put(letter, item);
                return item;
            };
            if ("firefly8".equals(key.getNamespace())) {
                registry.accept("symbol_stone_" + key.getPath(), sup);
            } else {
                registry.accept("symbol_stone_" + key.getNamespace() +
                        "__" + key.getPath(), sup);
            }
        });
    }

    static final Map<KeyedLetter, SymbolStoneBlockItem> LETTER_TO_ITEM = new HashMap<>();
    public static SymbolStoneBlockItem fromLetter(@Nullable KeyedLetter letter) {
        final SymbolStoneBlockItem item = LETTER_TO_ITEM.get(letter);
        if (item == null) return LETTER_TO_ITEM.get(KeyedLetter.empty());
        return item;
    }
}
