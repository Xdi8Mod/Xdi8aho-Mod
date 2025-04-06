package top.xdi8.mod.firefly8;

import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.registry.menu.MenuRegistry;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import top.xdi8.mod.firefly8.block.entity.FireflyBlockEntityTypes;
import top.xdi8.mod.firefly8.client.TakeOnlyContainerScreen;
import top.xdi8.mod.firefly8.client.Xdi8TableScreen;
import top.xdi8.mod.firefly8.entity.FireflyEntityTypes;
import top.xdi8.mod.firefly8.screen.FireflyMenus;

public class Firefly8Client implements Runnable {
    @Override
    public void run() {
        Firefly8Client.registerRenderTypes();
        BlockEntityRendererRegistry.register((BlockEntityType) FireflyBlockEntityTypes.REDWOOD_SIGN.get(), SignRenderer::new);
        ColorHandlerRegistry.registerBlockColors((blockState, blockAndTintGetter, blockPos, i) -> {
            if (blockAndTintGetter == null || blockPos == null) {
                return FoliageColor.FOLIAGE_DEFAULT;
            }
            return BiomeColors.getAverageFoliageColor(blockAndTintGetter, blockPos);
        }, FireflyBlocks.CEDAR_LEAVES);
        EntityRendererRegistry.register(FireflyEntityTypes.FIREFLY, NoopRenderer::new);
        MenuRegistry.registerScreenFactory(FireflyMenus.TAKE_ONLY_CHEST.get(), TakeOnlyContainerScreen::new);
        MenuRegistry.registerScreenFactory(FireflyMenus.XDI8_TABLE.get(), Xdi8TableScreen::new);
    }

    public static void registerRenderTypes(){
        RenderTypeRegistry.register(RenderType.translucent(), FireflyBlocks.XDI8AHO_PORTAL_TOP_BLOCK.get());
        RenderTypeRegistry.register(RenderType.cutoutMipped(), FireflyBlocks.CEDAR_LEAVES.get());
        RenderTypeRegistry.register(RenderType.cutout(), FireflyBlocks.XDI8AHO_BACK_FIRE_BLOCK.get(), FireflyBlocks.CEDAR_SAPLING.get(),
                FireflyBlocks.CEDAR_TRAPDOOR.get(), FireflyBlocks.CEDAR_DOOR.get(), FireflyBlocks.POTTED_CEDAR_SAPLING.get());
    }
}
