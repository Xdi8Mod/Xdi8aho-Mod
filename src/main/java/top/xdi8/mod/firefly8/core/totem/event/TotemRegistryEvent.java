package top.xdi8.mod.firefly8.core.totem.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.IModBusEvent;
import top.xdi8.mod.firefly8.core.totem.TotemAbility;

import java.util.function.BiConsumer;

public class TotemRegistryEvent extends Event implements IModBusEvent {
    private final BiConsumer<ResourceLocation, TotemAbility> registry;

    public TotemRegistryEvent(BiConsumer<ResourceLocation, TotemAbility> registry) {
        this.registry = registry;
    }

    public void register(ResourceLocation id, TotemAbility ability) {
        registry.accept(id, ability);
    }
}
