package org.featurehouse.mcfgmod.firefly8.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.CampfireSmokeParticle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import org.jetbrains.annotations.NotNull;

public class FireflyParticle extends TextureSheetParticle {
    public FireflyParticle(ClientLevel level, double x, double y, double z, double xs, double ys, double zs) {
        super(level, x, y, z, xs, ys, zs);
        this.setLifetime(80 + random.nextInt(16));
    }

    @Override
    protected int getLightColor(float pPartialTick) {
        //return (int)(alpha * 15) << 4;
        return 15 << 4; // TODO test visibility
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    /** @see CampfireSmokeParticle#tick()  */
    @Override
    public void tick() {
        if (this.age <= 20) {
            this.setAlpha(this.age * 0.05F);
        } else if (this.age >= this.getLifetime() - 20) {
            this.alpha -= 0.05F;
        }
        if (this.age >= this.getLifetime() && this.alpha <= 0) {
            this.remove();
        }
    }
}
