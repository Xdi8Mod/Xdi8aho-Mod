package top.xdi8.mod.firefly8.particle;

import net.minecraft.core.particles.SimpleParticleType;
import org.featurehouse.mcmod.spm.platform.api.reg.PlatformRegister;
import top.xdi8.mod.firefly8.util.InternalRegistryLogWrapper;

import java.util.function.Supplier;

public class FireflyParticles {
    public static final InternalRegistryLogWrapper LOG_WRAPPER = InternalRegistryLogWrapper.firefly8("particles");

    public static final Supplier<SimpleParticleType> FIREFLY
            = PlatformRegister.of("firefly8").particleType("firefly",
            () -> new SimpleParticleType(true));
}
