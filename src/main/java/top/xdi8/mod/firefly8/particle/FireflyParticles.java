package top.xdi8.mod.firefly8.particle;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FireflyParticles {
    public static final DeferredRegister<ParticleType<?>> REGISTRY
            = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, "firefly8");
    public static final RegistryObject<SimpleParticleType> FIREFLY
            = REGISTRY.register("firefly", () -> new SimpleParticleType(true));
}
