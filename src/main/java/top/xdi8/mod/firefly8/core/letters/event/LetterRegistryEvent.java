package top.xdi8.mod.firefly8.core.letters.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.IModBusEvent;
import top.xdi8.mod.firefly8.core.letters.KeyedLetter;

import java.util.Objects;
import java.util.function.BiConsumer;

public class LetterRegistryEvent extends Event implements IModBusEvent {
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
