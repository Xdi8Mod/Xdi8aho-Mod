package top.xdi8.mod.firefly8.core.letters;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;
import top.xdi8.mod.firefly8.core.letters.event.Xdi8RegistryEvents;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public final class LettersUtil {
    private LettersUtil() {}
    private static final BiMap<ResourceLocation, KeyedLetter> LETTER_MAP = HashBiMap.create(DefaultXdi8Letters.BY_ID);
    // Mutable
    private static final List<ResourceLocation> ID_LIST =
            new ArrayList<>(DefaultXdi8Letters.BY_ID.keySet().stream()
                    .sorted(Comparator.comparingInt(id -> DefaultXdi8Letters.BY_ID.get(id).lowercase()))
                    .collect(Collectors.toList()));

    public static KeyedLetter byId(ResourceLocation id) {
        return LETTER_MAP.getOrDefault(id, KeyedLetter.empty());
    }

    @Deprecated
    public static ResourceLocation getId(KeyedLetter letter) {
        return LETTER_MAP.inverse().getOrDefault(letter, EmptyLetter.ID);
    }

    public static void forEach(BiConsumer<ResourceLocation, KeyedLetter> action) {
        for (var id : ID_LIST) {
            action.accept(id, LETTER_MAP.get(id));
        }
    }

    @ApiStatus.Internal
    public static void fireLetterRegistry() {
        Xdi8RegistryEvents.LETTER.invoker().accept((key, value) -> {
            LETTER_MAP.put(key, value);
            ID_LIST.add(key);
        });
    }
}
