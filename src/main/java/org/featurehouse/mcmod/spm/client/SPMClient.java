package org.featurehouse.mcmod.spm.client;

import com.google.common.collect.ImmutableSet;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.platform.api.ClientOnly;
import org.featurehouse.mcmod.spm.platform.api.client.BlockRenderTypes;

@ClientOnly
public class SPMClient {
    //@Override
    public void onInitializeClient() {
        /* Client Screens */
        MenuScreens.register(SPMMain.GRINDER_SCREEN_HANDLER_TYPE.get(), GrinderScreen::new);
        MenuScreens.register(SPMMain.MAGIC_CUBE_SCREEN_HANDLER_TYPE.get(), MagicCubeScreen::new);
        MenuScreens.register(SPMMain.SEED_UPDATER_SCREEN_HANDLER_TYPE.get(), SeedUpdaterScreen::new);

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
