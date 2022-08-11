package org.featurehouse.mcmod.spm.platform.api.event;

import java.util.function.Consumer;

public final class Events {
    /** Return: event is canceled */
    public static boolean fireModbusEvent(ModbusEvent event) {
        return net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext.get().getModEventBus().post(event);
    }

    /** Return: event is canceled */
    public static boolean fireForgeBusEvent(BaseEvent event) {
        return net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
    }

    public static <E extends BaseEvent> void registerModbusEvent(Class<E> eventClass, Consumer<? super E> consumer) {
        net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext.get().getModEventBus()
                .addListener(net.minecraftforge.eventbus.api.EventPriority.NORMAL,
                        /*receiveCanceled*/false,
                        eventClass, consumer::accept);
    }

    public static <E extends ModbusEvent> void registerForgeBusEvent(Class<E> eventClass, Consumer<? super E> consumer) {
        net.minecraftforge.common.MinecraftForge.EVENT_BUS
                .addListener(net.minecraftforge.eventbus.api.EventPriority.NORMAL,
                        /*receiveCanceled*/false,
                        eventClass, consumer::accept);
    }

    private Events() {}
}
