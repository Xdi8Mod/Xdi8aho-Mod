package top.xdi8.mod.firefly8.core.letters.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import net.minecraft.resources.ResourceLocation;
import top.xdi8.mod.firefly8.core.letters.KeyedLetter;
import top.xdi8.mod.firefly8.core.totem.TotemAbility;

import java.util.function.Consumer;

@FunctionalInterface
public interface Xdi8RegistryEvents<T> {
    Event<Consumer<Xdi8RegistryEvents<KeyedLetter>>> LETTER = EventFactory.createConsumerLoop();
    Event<Consumer<Xdi8RegistryEvents<TotemAbility>>> TOTEM = EventFactory.createConsumerLoop();

    void register(ResourceLocation id, T item);
}
