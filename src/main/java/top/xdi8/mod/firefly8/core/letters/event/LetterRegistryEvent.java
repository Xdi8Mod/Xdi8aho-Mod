package top.xdi8.mod.firefly8.core.letters.event;

import net.minecraft.resources.ResourceLocation;
import org.featurehouse.mcmod.spm.platform.api.event.ModbusEvent;
import top.xdi8.mod.firefly8.core.letters.KeyedLetter;

import java.util.Objects;
import java.util.function.BiConsumer;

public class LetterRegistryEvent extends ModbusEvent {
    private final BiConsumer<ResourceLocation, KeyedLetter> consumer;

    public LetterRegistryEvent(BiConsumer<ResourceLocation, KeyedLetter> consumer) {
        this.consumer = consumer;
    }

    public void register(ResourceLocation id, KeyedLetter letter) {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(letter, "letter");
        this.consumer.accept(id, letter);
    }
}
