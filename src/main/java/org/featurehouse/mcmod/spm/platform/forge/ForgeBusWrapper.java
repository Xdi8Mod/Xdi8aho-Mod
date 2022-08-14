package org.featurehouse.mcmod.spm.platform.forge;

import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import org.featurehouse.mcmod.spm.platform.api.event.BaseEvent;
import org.featurehouse.mcmod.spm.platform.api.event.BusWrapper;

import java.util.function.Consumer;

public record ForgeBusWrapper(IEventBus bus) implements BusWrapper {
    @Override
    public boolean fire(BaseEvent event) {
        return bus().post(event);
    }

    @Override
    public <E extends BaseEvent> void register(Class<E> eventClass, Consumer<? super E> consumer) {
        bus().addListener(EventPriority.NORMAL, /*receivedCanceled=*/false, eventClass, consumer::accept);
    }
}
