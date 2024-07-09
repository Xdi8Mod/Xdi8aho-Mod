package top.xdi8.mod.firefly8.forge;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import top.xdi8.mod.firefly8.Firefly8;

@Mod.EventBusSubscriber(modid = "firefly8", bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModBusEvents {
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(Firefly8::commonSetup);
    }
}
