package org.featurehouse.mcmod.spm.platform.api.event;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.featurehouse.mcmod.spm.platform.forge.ForgeBusWrapper;

final class EventsImpl {
    static BusWrapper modBus() {
        return new ForgeBusWrapper(FMLJavaModLoadingContext.get().getModEventBus());
    }

    static BusWrapper forgeBus() {
        return new ForgeBusWrapper(MinecraftForge.EVENT_BUS);
    }
}
