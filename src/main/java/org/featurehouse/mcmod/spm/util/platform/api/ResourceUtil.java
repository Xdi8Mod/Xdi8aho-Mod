package org.featurehouse.mcmod.spm.util.platform.api;

import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.featurehouse.mcmod.spm.resource.magicalenchantment.MagicalEnchantmentLoader;

public final class ResourceUtil {
    public static void loadResource() {}    // Forge: NOOP

    @Mod.EventBusSubscriber(modid = "sweet_potato")
    public static final class ForgeImpl {
        @SubscribeEvent
        public static void forge$addReloadListener(AddReloadListenerEvent event) {
            event.addListener(new MagicalEnchantmentLoader());
        }
    }
}
