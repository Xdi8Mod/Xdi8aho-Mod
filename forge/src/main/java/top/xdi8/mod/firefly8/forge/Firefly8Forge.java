package top.xdi8.mod.firefly8.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.xdi8.mod.firefly8.Firefly8;
import top.xdi8.mod.firefly8.FireflyClientSetup;

@Mod("firefly8")
public class Firefly8Forge {
    public Firefly8Forge() {
        EventBuses.registerModEventBus("firefly8", FMLJavaModLoadingContext.get().getModEventBus());
        Firefly8.init();
    }

    @Mod.EventBusSubscriber(value = Dist.CLIENT, modid = "firefly8", bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class ClientSetup {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(new FireflyClientSetup());
        }
    }
}
