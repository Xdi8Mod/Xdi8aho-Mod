package top.xdi8.mod.firefly8.forge;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import top.xdi8.mod.firefly8.Firefly8Client;

@EventBusSubscriber(value = Dist.CLIENT, modid = "firefly8", bus = EventBusSubscriber.Bus.MOD)
public class Firefly8ClientForge {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(new Firefly8Client());
        Firefly8Client.registerRenderTypes();
    }
}
