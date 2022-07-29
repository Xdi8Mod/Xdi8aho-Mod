package top.xdi8.mod.firefly8;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.featurehouse.mcmod.spm.platform.api.client.BlockRenderTypes;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import top.xdi8.mod.firefly8.client.ChiselScreen;
import top.xdi8.mod.firefly8.client.FireflyParticle;
import top.xdi8.mod.firefly8.client.TakeOnlyContainerScreen;
import top.xdi8.mod.firefly8.client.Xdi8TableScreen;
import top.xdi8.mod.firefly8.entity.FireflyEntityTypes;
import top.xdi8.mod.firefly8.particle.FireflyParticles;
import top.xdi8.mod.firefly8.screen.FireflyMenus;

@Mod.EventBusSubscriber(modid = "firefly8", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FireflyClientSetup implements Runnable {
    @Override
    public void run() {
        EntityRenderers.register(FireflyEntityTypes.FIREFLY.get(), NoopRenderer::new);
        MenuScreens.register(FireflyMenus.TAKE_ONLY_CHEST.get(),
                TakeOnlyContainerScreen::new);
        MenuScreens.register(FireflyMenus.CHISEL.get(),
                ChiselScreen::new);
        MenuScreens.register(FireflyMenus.XDI8_TABLE.get(),
                Xdi8TableScreen::new);
        BlockRenderTypes.register(RenderType.cutoutMipped(),
                FireflyBlocks.XDI8AHO_PORTAL_TOP_BLOCK);
    }

    @SubscribeEvent
    public static void registerParticles(ParticleFactoryRegisterEvent ignore1) {
        Minecraft.getInstance().particleEngine.register(FireflyParticles.FIREFLY.get(),
                pSprites -> (pType, pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed) -> {
                    var particle = new FireflyParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
                    particle.pickSprite(pSprites);
                    return particle;
                });
    }

    @SubscribeEvent
    public static void runClientWorks(FMLClientSetupEvent event) {
        event.enqueueWork(new FireflyClientSetup());
    }
}
