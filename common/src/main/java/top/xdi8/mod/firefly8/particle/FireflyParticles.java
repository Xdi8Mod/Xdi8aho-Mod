package top.xdi8.mod.firefly8.particle;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.particles.SimpleParticleType;
import org.featurehouse.mcmod.spm.platform.api.reg.PlatformRegister;
import top.xdi8.mod.firefly8.util.InternalRegistryLogWrapper;

public class FireflyParticles {
    public static final InternalRegistryLogWrapper LOG_WRAPPER = InternalRegistryLogWrapper.firefly8("particles");

    public static final RegistrySupplier<SimpleParticleType> FIREFLY
            = PlatformRegister.of("firefly8").particleType("firefly",
            () -> new SimpleParticleType(true) {});
}
