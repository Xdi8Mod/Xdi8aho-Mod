package top.xdi8.mod.firefly8.fabric;

import dev.architectury.registry.client.particle.ParticleProviderRegistry;
import net.fabricmc.api.ClientModInitializer;
import top.xdi8.mod.firefly8.Firefly8Client;
import top.xdi8.mod.firefly8.client.FireflyParticle;
import top.xdi8.mod.firefly8.particle.FireflyParticles;

public class FireflyClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        new Firefly8Client().run();
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
}
