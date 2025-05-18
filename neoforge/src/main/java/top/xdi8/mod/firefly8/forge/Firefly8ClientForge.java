package top.xdi8.mod.firefly8.forge;

import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.special.StandingSignSpecialRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.RegisterSpecialBlockModelRendererEvent;
import top.xdi8.mod.firefly8.Firefly8Client;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import top.xdi8.mod.firefly8.client.FireflyParticle;
import top.xdi8.mod.firefly8.entity.FireflyEntityTypes;
import top.xdi8.mod.firefly8.particle.FireflyParticles;

public class Firefly8ClientForge {
    public Firefly8ClientForge() { }

    @EventBusSubscriber(value = Dist.CLIENT, modid = "firefly8", bus = EventBusSubscriber.Bus.MOD)
    public static final class Firefly8ClientEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(new Firefly8Client());
        }

        @SubscribeEvent
        public static void registerSpecialBlockRenderers(RegisterSpecialBlockModelRendererEvent event) {
            event.register(FireflyBlocks.CEDAR_SIGN.get(), new StandingSignSpecialRenderer.Unbaked(FireflyBlocks.redwoodType));
        }

        @SubscribeEvent
        public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(FireflyParticles.FIREFLY.get(), FireflyParticle.Provider::new);
        }

        @SubscribeEvent
        public static void event(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(FireflyEntityTypes.FIREFLY.get(), NoopRenderer::new);
        }
    }
}
