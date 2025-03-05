package top.xdi8.mod.firefly8.block.symbol;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.xdi8.mod.firefly8.Firefly8;
import top.xdi8.mod.firefly8.core.letters.KeyedLetter;
import top.xdi8.mod.firefly8.core.letters.LettersUtil;

import java.util.*;
import java.util.function.Function;

public class SymbolStoneBlock extends Block implements KeyedLetter.Provider {
    private final KeyedLetter letter;

    public SymbolStoneBlock(BlockBehaviour.Properties properties, KeyedLetter letter) {
        super(properties);
        this.letter = letter;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Item.@NotNull TooltipContext context,
                                @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        if (letter.isNull()) return;
        List<String> sb = new ArrayList<>();
        if (letter.hasUppercase()) sb.add(Character.toString(letter.uppercase()));
        if (letter.hasMiddleCase()) sb.add(Character.toString(letter.middleCase()));
        if (letter.hasLowercase()) sb.add(Character.toString(letter.lowercase()));
        String s = join(sb.iterator(), " ");
        if (!s.isBlank()) {
            tooltip.add(Component.translatable("block.firefly8.symbol_stone.letter", s));
        }
    }

    @NotNull
    @Override
    public KeyedLetter letter() {
        return letter;
    }

    @ApiStatus.Internal
    public static void registerAll(Consumer3<String, Function<BlockBehaviour.Properties, Block>, BlockBehaviour.Properties> registry) {
        BlockBehaviour.Properties properties1 = Properties.of()
                .overrideDescription("block.firefly8.symbol_stone")
                .mapColor(MapColor.COLOR_LIGHT_GRAY)
                .requiresCorrectToolForDrops()
                .strength(1.5F, 8.0F);
        registry.accept("symbol_stone", (properties) -> {
            var block = new SymbolStoneBlock(properties, KeyedLetter.empty());
            LETTER_TO_BLOCK.put(KeyedLetter.empty(), block);
            return block;
        }, properties1);
        LettersUtil.forEach((key, letter) -> {
            if (letter.isNull()) return;
            Function<BlockBehaviour.Properties, Block> sup = (properties) -> {
                var block = new SymbolStoneBlock(properties, letter);
                LETTER_TO_BLOCK.put(letter, block);
                return block;
            };
            registry.accept(getBlockId(key), sup, properties1);
        });
    }

    static final Map<KeyedLetter, SymbolStoneBlock> LETTER_TO_BLOCK = new HashMap<>();
    public static SymbolStoneBlock fromLetter(@Nullable KeyedLetter letter) {
        final SymbolStoneBlock block = LETTER_TO_BLOCK.get(letter);
        if (block == null) return LETTER_TO_BLOCK.get(KeyedLetter.empty());
        return block;
    }

    public static String getBlockId(ResourceLocation key){
        if (Firefly8.MODID.equals(key.getNamespace())) return "symbol_stone_" + key.getPath();
        else return "symbol_stone_" + key.getNamespace() + "__" + key.getPath();
    }

    // from plexus-utils
    @SuppressWarnings("SameParameterValue")
    private static String join(Iterator<?> iterator, String separator) {
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

    @FunctionalInterface
    public interface Consumer3<T1, T2, T3>{
        void accept(T1 t1, T2 t2, T3 t3);
    }
}
