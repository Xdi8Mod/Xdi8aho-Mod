package org.featurehouse.mcmod.spm.client;

import com.google.common.collect.ImmutableSet;
import dev.architectury.registry.menu.MenuRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.platform.api.client.BlockRenderTypes;

@Environment(EnvType.CLIENT)
public class SPMClient {
    //@Override
    public void onInitializeClient() {
        /* Client Screens */
        MenuRegistry.registerScreenFactory(SPMMain.GRINDER_SCREEN_HANDLER_TYPE.get(), GrinderScreen::new);
        MenuRegistry.registerScreenFactory(SPMMain.MAGIC_CUBE_SCREEN_HANDLER_TYPE.get(), MagicCubeScreen::new);
        MenuRegistry.registerScreenFactory(SPMMain.SEED_UPDATER_SCREEN_HANDLER_TYPE.get(), SeedUpdaterScreen::new);

        /* Rendering */

        BlockRenderTypes.register(RenderType.cutout(),
                ImmutableSet.of(SPMMain.PURPLE_POTATO_CROP, SPMMain.RED_POTATO_CROP,
                SPMMain.WHITE_POTATO_CROP, SPMMain.SEED_UPDATER,
                SPMMain.ENCHANTED_SAPLING,
                SPMMain.ENCHANTED_TUBER, SPMMain.ENCHANTED_CROPS
                )
        );
    }
    /* @Deprecated */
    private static final class ColorProviding {
        static void init() {}
    }
    public static void initColorProviders() { ColorProviding.init(); }
}
