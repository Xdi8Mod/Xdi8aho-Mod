package top.xdi8.mod.firefly8.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;

public class FireflyParticle extends TextureSheetParticle {
    public FireflyParticle(ClientLevel level, double x, double y, double z, double xs, double ys, double zs) {
        super(level, x, y, z, xs, ys, zs);
        this.setLifetime(80 + random.nextInt(16));
    }

    @Override
    protected int getLightColor(float pPartialTick) {
        return 240;
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    /** @see CampfireSmokeParticle#tick()  */
    @Override
    public void tick() {
        if (this.age++ <= 20) {
            this.setAlpha(this.age * 0.05F);
        } else if (this.age >= this.getLifetime() - 20) {
            this.alpha -= 0.05F;
        }
        if (this.age >= this.getLifetime() && this.alpha <= 0) {
            this.remove();
        }
    }

    @Environment(EnvType.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet sprites) {
            this.sprite = sprites;
        }

        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            FireflyParticle particle = new FireflyParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.pickSprite(sprite);
            particle.setAlpha(1.0F);
            return particle;
        }
    }
}
