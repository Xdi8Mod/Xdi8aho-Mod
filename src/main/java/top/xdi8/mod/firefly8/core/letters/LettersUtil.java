package top.xdi8.mod.firefly8.core.letters;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import org.jetbrains.annotations.ApiStatus;
import top.xdi8.mod.firefly8.core.letters.event.LetterRegistryEvent;

import java.util.Optional;
import java.util.function.Function;

public final class LettersUtil {
    private LettersUtil() {}
    private static final BiMap<ResourceLocation, KeyedLetter> LETTER_MAP = HashBiMap.create(DefaultXdi8Letters.BY_ID);

    public static KeyedLetter byId(ResourceLocation id) {
        return LETTER_MAP.getOrDefault(id, KeyedLetter.empty());
    }

    public static ResourceLocation getId(KeyedLetter letter) {
        return LETTER_MAP.inverse().getOrDefault(letter, EmptyLetter.ID);
    }

    public static <T> Optional<T> idToResource(KeyedLetter letter,
                                               Function<ResourceLocation, Optional<T>> resourceMapper) {
        if (letter.isNull()) return Optional.empty();
        return resourceMapper.apply(letter.id());
    }

    public static <T> Optional<T> letterToResource(KeyedLetter letter,
                                                   Function<KeyedLetter, Optional<T>> resourceMapper) {
        if (letter.isNull()) return Optional.empty();
        return resourceMapper.apply(letter);
    }

    @ApiStatus.Internal
    public static void fireLetterRegistry(IEventBus modBus) {
        modBus.post(new LetterRegistryEvent(LETTER_MAP::put));
    }
}
