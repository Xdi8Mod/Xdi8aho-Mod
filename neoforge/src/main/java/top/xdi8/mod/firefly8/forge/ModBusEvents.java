package top.xdi8.mod.firefly8.forge;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import top.xdi8.mod.firefly8.Firefly8;

@EventBusSubscriber(modid = "firefly8", bus = EventBusSubscriber.Bus.MOD)
public final class ModBusEvents {
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(Firefly8::commonSetup);
    }
}
