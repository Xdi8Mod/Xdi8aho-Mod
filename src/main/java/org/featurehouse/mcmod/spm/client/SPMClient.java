package org.featurehouse.mcmod.spm.client;

import com.google.common.collect.ImmutableSet;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.FoliageColor;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.platform.api.ClientOnly;
import org.featurehouse.mcmod.spm.platform.api.client.BlockRenderTypes;
import org.featurehouse.mcmod.spm.platform.api.client.ColorProviders;

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
                SPMMain.ENCHANTED_ACACIA_SAPLING, SPMMain.ENCHANTED_BIRCH_SAPLING,
                SPMMain.ENCHANTED_DARK_OAK_SAPLING, SPMMain.ENCHANTED_OAK_SAPLING,
                SPMMain.ENCHANTED_JUNGLE_SAPLING, SPMMain.ENCHANTED_SPRUCE_SAPLING,
                SPMMain.ENCHANTED_TUBER, SPMMain.ENCHANTED_CROPS
                )
        );
    }
    private static final class ColorProviding {
        static {
            ColorProviders.getBlock().register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getAverageFoliageColor(world, pos) : FoliageColor.getDefaultColor(),
                    ImmutableSet.of(SPMMain.ENCHANTED_ACACIA_LEAVES, SPMMain.ENCHANTED_DARK_OAK_LEAVES,
                            SPMMain.ENCHANTED_JUNGLE_LEAVES, SPMMain.ENCHANTED_OAK_LEAVES)
            );
            ColorProviders.getBlock().register(SPMMain.ENCHANTED_BIRCH_LEAVES, (state, world, pos, tintIndex) -> FoliageColor.getBirchColor());
            ColorProviders.getBlock().register(SPMMain.ENCHANTED_SPRUCE_LEAVES, (state, world, pos, tintIndex) -> FoliageColor.getEvergreenColor());
            ColorProviders.getItem().register((stack, tintIndex) -> FoliageColor.getDefaultColor(),
                    ImmutableSet.of(SPMMain.ENCHANTED_ACACIA_LEAVES_ITEM, SPMMain.ENCHANTED_DARK_OAK_LEAVES_ITEM,
                            SPMMain.ENCHANTED_JUNGLE_LEAVES_ITEM, SPMMain.ENCHANTED_OAK_LEAVES_ITEM)
            );
            ColorProviders.getItem().register(SPMMain.ENCHANTED_BIRCH_LEAVES_ITEM, (stack, tintIndex) -> FoliageColor.getBirchColor());
            ColorProviders.getItem().register(SPMMain.ENCHANTED_SPRUCE_LEAVES_ITEM, (stack, tintIndex) -> FoliageColor.getEvergreenColor());

        }

        static void init() {}
    }
    public static void initColorProviders() { ColorProviding.init(); }
}
