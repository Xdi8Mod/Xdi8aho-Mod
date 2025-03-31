package top.xdi8.mod.firefly8.particle;

import dev.architectury.registry.registries.RegistrySupplier;
import io.github.qwerty770.mcmod.xdi8.registries.RegistryHelper;
import net.minecraft.core.particles.SimpleParticleType;
import io.github.qwerty770.mcmod.xdi8.registries.InternalRegistryLogWrapper;

public class FireflyParticles {
    public static final InternalRegistryLogWrapper LOG_WRAPPER = InternalRegistryLogWrapper.firefly8("particles");
    public static final RegistrySupplier<SimpleParticleType> FIREFLY =
            RegistryHelper.particleType("firefly", () -> new SimpleParticleType(true) {});
}
