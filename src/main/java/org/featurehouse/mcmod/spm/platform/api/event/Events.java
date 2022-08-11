package org.featurehouse.mcmod.spm.platform.api.event;

import com.google.errorprone.annotations.CanIgnoreReturnValue;

import java.util.function.Consumer;

public final class Events {
    /** Return: event is canceled */
    @CanIgnoreReturnValue
    public static boolean fireModbusEvent(ModbusEvent event) {
        return EventsImpl.modBus().fire(event);
    }

    /** Return: event is canceled */
    @CanIgnoreReturnValue
    public static boolean fireForgeBusEvent(BaseEvent event) {
        return EventsImpl.forgeBus().fire(event);
    }

    public static <E extends BaseEvent> void registerModbusEvent(Class<E> eventClass, Consumer<? super E> consumer) {
        EventsImpl.modBus().register(eventClass, consumer);
    }

    public static <E extends ModbusEvent> void registerForgeBusEvent(Class<E> eventClass, Consumer<? super E> consumer) {
        EventsImpl.forgeBus().register(eventClass, consumer);
    }

    private Events() {}
}
