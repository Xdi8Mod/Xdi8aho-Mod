package top.xdi8.mod.firefly8;

import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.particle.ParticleProviderRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.registry.menu.MenuRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.world.level.FoliageColor;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import top.xdi8.mod.firefly8.client.FireflyParticle;
import top.xdi8.mod.firefly8.client.TakeOnlyContainerScreen;
import top.xdi8.mod.firefly8.client.Xdi8TableScreen;
import top.xdi8.mod.firefly8.entity.FireflyEntityTypes;
import top.xdi8.mod.firefly8.particle.FireflyParticles;
import top.xdi8.mod.firefly8.screen.FireflyMenus;

public class Firefly8Client implements Runnable {
    @Override
    public void run() {
        EntityRendererRegistry.register(FireflyEntityTypes.FIREFLY, NoopRenderer::new);
        ColorHandlerRegistry.registerBlockColors((blockState, blockAndTintGetter, blockPos, i) -> {
            if (blockAndTintGetter == null || blockPos == null) {
                return FoliageColor.FOLIAGE_DEFAULT;
            }
            return BiomeColors.getAverageFoliageColor(blockAndTintGetter, blockPos);
        }, FireflyBlocks.CEDAR_LEAVES);
        MenuRegistry.registerScreenFactory(FireflyMenus.TAKE_ONLY_CHEST.get(), TakeOnlyContainerScreen::new);
        MenuRegistry.registerScreenFactory(FireflyMenus.XDI8_TABLE.get(), Xdi8TableScreen::new);
    }

    public static void registerRenderTypes(){
        RenderTypeRegistry.register(RenderType.cutoutMipped(), FireflyBlocks.CEDAR_LEAVES.get());
        RenderTypeRegistry.register(RenderType.cutout(), FireflyBlocks.XDI8AHO_BACK_FIRE_BLOCK.get(), FireflyBlocks.CEDAR_SAPLING.get(),
                FireflyBlocks.CEDAR_TRAPDOOR.get(), FireflyBlocks.CEDAR_DOOR.get(), FireflyBlocks.XDI8AHO_PORTAL_TOP_BLOCK.get());
    }

    @Environment(EnvType.CLIENT)
    public static void registerParticles() {
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
