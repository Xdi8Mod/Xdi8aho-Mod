package top.xdi8.mod.firefly8.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.SpecialBlockRendererRegistry;
import net.minecraft.client.renderer.special.StandingSignSpecialRenderer;
import top.xdi8.mod.firefly8.Firefly8Client;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import top.xdi8.mod.firefly8.client.FireflyParticle;
import top.xdi8.mod.firefly8.particle.FireflyParticles;

public class Firefly8ClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        new Firefly8Client().run();
        SpecialBlockRendererRegistry.register(FireflyBlocks.CEDAR_SIGN.get(), new StandingSignSpecialRenderer.Unbaked(FireflyBlocks.redwoodType));
        ParticleFactoryRegistry.getInstance().register(FireflyParticles.FIREFLY.get(), FireflyParticle.Provider::new);
    }
}
