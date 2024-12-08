package top.xdi8.mod.firefly8.item.symbol;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.xdi8.mod.firefly8.block.symbol.SymbolStoneBlock;
import top.xdi8.mod.firefly8.core.letters.KeyedLetter;
import top.xdi8.mod.firefly8.core.letters.LettersUtil;
import top.xdi8.mod.firefly8.item.FireflyItems;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public class SymbolStoneBlockItem extends BlockItem implements KeyedLetter.Provider {
    private final KeyedLetter letter;

    public SymbolStoneBlockItem(KeyedLetter letter, Properties properties) {
        super(SymbolStoneBlock.fromLetter(letter), properties);
        this.letter = letter;
    }

    @Override
    public void onDestroyed(@NotNull ItemEntity pItemEntity) {
        ItemUtils.onContainerDestroyed(pItemEntity, Stream.of(
                new ItemStack(FireflyItems.DARK_SYMBOL_STONE.get(), pItemEntity.getItem().getCount())).toList());
    }

    public SymbolStoneBlockItem withLetter(KeyedLetter letter) {
        return fromLetter(letter);
    }

    @NotNull
    @Override
    public KeyedLetter letter() {
        return letter;
    }

    @ApiStatus.Internal
    public static void registerAll(SymbolStoneBlock.Consumer3<String, Function<Item.Properties, Item>, Item.Properties> registry) {
        Item.Properties properties1 = new Properties()
                .rarity(Rarity.UNCOMMON)
                .arch$tab(FireflyItems.TAB);
        registry.accept("symbol_stone", (properties) -> {
            var item = new SymbolStoneBlockItem(KeyedLetter.empty(), properties);
            LETTER_TO_ITEM.put(KeyedLetter.empty(), item);
            return item;
        }, properties1);
        LettersUtil.forEach((key, letter) -> {
            if (letter.isNull()) return;
            Function<Item.Properties, Item> sup = (properties) -> {
                var item = new SymbolStoneBlockItem(letter, properties);
                LETTER_TO_ITEM.put(letter, item);
                return item;
            };
            if ("firefly8".equals(key.getNamespace())) {
                registry.accept("symbol_stone_" + key.getPath(), sup, properties1);
            } else {
                registry.accept("symbol_stone_" + key.getNamespace() +
                        "__" + key.getPath(), sup, properties1);
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
