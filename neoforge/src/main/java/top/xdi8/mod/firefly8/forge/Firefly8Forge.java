package top.xdi8.mod.firefly8.forge;

import dev.architectury.registry.client.particle.ParticleProviderRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.neoforgespi.Environment;
import top.xdi8.mod.firefly8.Firefly8;
import top.xdi8.mod.firefly8.Firefly8Client;
import top.xdi8.mod.firefly8.client.FireflyParticle;
import top.xdi8.mod.firefly8.network.FireflyNetwork;
import top.xdi8.mod.firefly8.particle.FireflyParticles;

@Mod("firefly8")
public class Firefly8Forge {
    public Firefly8Forge() {
        Firefly8.init();
        if (Environment.get().getDist().equals(Dist.CLIENT)){
            FireflyNetwork.registerClientNetwork();
            registerParticles();
        }
        FireflyNetwork.registerServerNetwork();
    }

    @OnlyIn(Dist.CLIENT)
    public void registerParticles() {
        // Register the firefly particle provider at a correct point, to fix this error
        // "Something is attempting to register particle providers at a later point than intended! This might cause issues!"
        // java.lang.Throwable: null at dev.architectury.registry.client.particle.forge.ParticleProviderRegistryImpl.register(ParticleProviderRegistryImpl.java:100) ~[architectury-forge-4.11.91.jar%2377!/:?]
        ParticleProviderRegistry.register(FireflyParticles.FIREFLY,
                pSprites -> (pType, pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed) -> {
                    var particle = new FireflyParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
                    particle.pickSprite(pSprites);
                    return particle;
                });
    }

    @EventBusSubscriber(value = Dist.CLIENT, modid = "firefly8", bus = EventBusSubscriber.Bus.MOD)
    public static final class ClientSetup {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(new Firefly8Client());
            Firefly8Client.registerRenderTypes();
        }
    }

    @EventBusSubscriber(modid = "firefly8", bus = EventBusSubscriber.Bus.MOD)
    public static final class CommonSetup {
        @SubscribeEvent
        public static void onCommonSetup(FMLCommonSetupEvent event) {
            event.enqueueWork(Firefly8::commonSetup);
        }

        private static boolean registered = false;
        @SubscribeEvent
        public static void register(RegisterEvent event){
            if (!registered){
                Firefly8.activateRegistries();
                registered = true;
            }
        }
    }
}
