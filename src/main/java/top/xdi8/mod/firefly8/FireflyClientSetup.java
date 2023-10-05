package top.xdi8.mod.firefly8;

import dev.architectury.registry.client.particle.ParticleProviderRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.menu.MenuRegistry;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.world.level.FoliageColor;
import org.featurehouse.mcmod.spm.platform.api.client.BlockRenderTypes;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import top.xdi8.mod.firefly8.client.FireflyParticle;
import top.xdi8.mod.firefly8.client.TakeOnlyContainerScreen;
import top.xdi8.mod.firefly8.client.Xdi8TableScreen;
import top.xdi8.mod.firefly8.entity.FireflyEntityTypes;
import top.xdi8.mod.firefly8.particle.FireflyParticles;
import top.xdi8.mod.firefly8.screen.FireflyMenus;

//@Mod.EventBusSubscriber(modid = "firefly8", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FireflyClientSetup implements Runnable {
    @Override
    public void run() {
        // EntityRendererRegistry.register(FireflyEntityTypes.FIREFLY, NoopRenderer::new);
        EntityRenderers.register(FireflyEntityTypes.FIREFLY.get(), NoopRenderer::new);
        ColorHandlerRegistry.registerBlockColors((blockState, blockAndTintGetter, blockPos, i) -> {
            if (blockAndTintGetter == null || blockPos == null) {
                return FoliageColor.getDefaultColor();
            }
            return BiomeColors.getAverageFoliageColor(blockAndTintGetter, blockPos);
        }, FireflyBlocks.CEDAR_LEAVES);
        MenuRegistry.registerScreenFactory(FireflyMenus.TAKE_ONLY_CHEST.get(),
                TakeOnlyContainerScreen::new);
        MenuRegistry.registerScreenFactory(FireflyMenus.XDI8_TABLE.get(),
                Xdi8TableScreen::new);
        BlockRenderTypes.register(RenderType.cutoutMipped(),
                FireflyBlocks.XDI8AHO_PORTAL_TOP_BLOCK);
        BlockRenderTypes.register(RenderType.cutout(),
                FireflyBlocks.XDI8AHO_BACK_FIRE_BLOCK);
        ParticleProviderRegistry.register(FireflyParticles.FIREFLY.get(),
                pSprites -> (pType, pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed) -> {
                    var particle = new FireflyParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
                    particle.pickSprite(pSprites);
                    return particle;
                });
    }
}
