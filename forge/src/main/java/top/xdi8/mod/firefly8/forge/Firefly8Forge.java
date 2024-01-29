package top.xdi8.mod.firefly8.forge;

import dev.architectury.platform.forge.EventBuses;
import dev.architectury.registry.client.particle.ParticleProviderRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.Environment;
import top.xdi8.mod.firefly8.Firefly8;
import top.xdi8.mod.firefly8.FireflyClientSetup;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import top.xdi8.mod.firefly8.client.FireflyParticle;
import top.xdi8.mod.firefly8.network.FireflyNetwork;
import top.xdi8.mod.firefly8.particle.FireflyParticles;

@Mod("firefly8")
public class Firefly8Forge {
    public Firefly8Forge() {
        EventBuses.registerModEventBus("firefly8", FMLJavaModLoadingContext.get().getModEventBus());
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

    @Mod.EventBusSubscriber(value = Dist.CLIENT, modid = "firefly8", bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class ClientSetup {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(new FireflyClientSetup());
            RenderTypeRegistry.register(RenderType.cutoutMipped(), FireflyBlocks.XDI8AHO_PORTAL_TOP_BLOCK.get(), FireflyBlocks.CEDAR_LEAVES.get());
            RenderTypeRegistry.register(RenderType.cutout(), FireflyBlocks.XDI8AHO_BACK_FIRE_BLOCK.get(), FireflyBlocks.CEDAR_SAPLING.get(),
                    FireflyBlocks.CEDAR_TRAPDOOR.get(), FireflyBlocks.CEDAR_DOOR.get());
        }
    }
}
