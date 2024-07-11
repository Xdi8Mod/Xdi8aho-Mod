package org.featurehouse.mcmod.spm.client;

import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.registry.menu.MenuRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.platform.api.client.ColorProviders;

import java.util.Map;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class SPMClient implements Runnable {
    @Override
    public void run() {
        // Client Screens
        MenuRegistry.registerScreenFactory(SPMMain.GRINDER_SCREEN_HANDLER_TYPE.get(), GrinderScreen::new);
        MenuRegistry.registerScreenFactory(SPMMain.MAGIC_CUBE_SCREEN_HANDLER_TYPE.get(), MagicCubeScreen::new);
        MenuRegistry.registerScreenFactory(SPMMain.SEED_UPDATER_SCREEN_HANDLER_TYPE.get(), SeedUpdaterScreen::new);

        // Item/Block Colors
        Map<Supplier<Item>, ItemColor> view = ColorProviders.getItem().view();
        SPMMain.getLogger().debug("ItemView: {}", view);
        view.forEach((itemSupplier, itemColor) -> ColorHandlerRegistry.registerItemColors(itemColor, itemSupplier));
        Map<Supplier<Block>, BlockColor> view2 = ColorProviders.getBlock().view();
        SPMMain.getLogger().debug("BlockView: {}", view2);
        view2.forEach((blockSupplier, blockColor) -> ColorHandlerRegistry.registerBlockColors(blockColor, blockSupplier));

        // Rendering
        registerRenderTypes();
    }

    public static void registerRenderTypes(){
        RenderTypeRegistry.register(RenderType.cutout(), SPMMain.PURPLE_POTATO_CROP.get(), SPMMain.RED_POTATO_CROP.get(),
                SPMMain.WHITE_POTATO_CROP.get(), SPMMain.SEED_UPDATER.get(), SPMMain.ENCHANTED_SAPLING.get(),
                SPMMain.ENCHANTED_TUBER.get(), SPMMain.ENCHANTED_CROPS.get());
    }
}
