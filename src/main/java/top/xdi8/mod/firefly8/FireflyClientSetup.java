package top.xdi8.mod.firefly8;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import top.xdi8.mod.firefly8.client.FireflyParticle;
import top.xdi8.mod.firefly8.entity.FireflyEntityTypes;
import top.xdi8.mod.firefly8.particle.FireflyParticles;

@Mod.EventBusSubscriber(modid = "firefly8", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FireflyClientSetup implements Runnable {
    @Override
    public void run() {
        EntityRenderers.register(FireflyEntityTypes.FIREFLY.get(), NoopRenderer::new);
    }

    @SubscribeEvent
    public static void registerParticles(ParticleFactoryRegisterEvent ignore1) {
        Minecraft.getInstance().particleEngine.register(FireflyParticles.FIREFLY.get(),
                pSprites -> (pType, pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed) -> {
                    var particle = new FireflyParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
                    particle.pickSprite(pSprites);
                    return particle;
                });
    }

    @SubscribeEvent
    public static void runClientWorks(FMLClientSetupEvent event) {
        event.enqueueWork(new FireflyClientSetup());
    }
}
