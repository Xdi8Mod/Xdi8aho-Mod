package org.featurehouse.mcmod.spm.platform.api.event;

import java.util.function.Consumer;

public interface BusWrapper {
    boolean fire(BaseEvent event);
    <E extends BaseEvent> void register(Class<E> eventClass, Consumer<? super E> consumer);
}
