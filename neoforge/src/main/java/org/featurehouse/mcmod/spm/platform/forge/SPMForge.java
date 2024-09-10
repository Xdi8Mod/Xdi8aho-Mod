package org.featurehouse.mcmod.spm.platform.forge;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.advancement.BalancedDietHelper;
import org.featurehouse.mcmod.spm.client.SPMClient;
import org.featurehouse.mcmod.spm.platform.api.reg.RegistryContainer;
import org.featurehouse.mcmod.spm.resource.magicalenchantment.MagicalEnchantmentLoader;
import org.jetbrains.annotations.ApiStatus;
import top.xdi8.mod.firefly8.advancement.AdvancementLoadingContext;
import top.xdi8.mod.firefly8.util.ResourceLocationTool;

@Mod(SPMMain.MODID)
@ApiStatus.Internal
public class SPMForge {
    private static final ResourceLocation BALANCED_DIET = ResourceLocationTool.withDefaultNamespace("husbandry/balanced_diet");

    public SPMForge() {
        SPMMain.getLogger().info("SPM initializing!");  // Don't you dare delete this line!
        RegistryContainer.of(SPMMain.MODID).subscribeModBus();
        AdvancementLoadingContext.EVENT.register(ctx -> {
            if (BALANCED_DIET.equals(ctx.id)) {
                BalancedDietHelper.setupCriteria(ctx);
            }
        });
    }

    @EventBusSubscriber(modid = "sweet_potato", bus = EventBusSubscriber.Bus.MOD)
    public static final class ModBusSubscribers {
        @SubscribeEvent
        public static void startup(FMLCommonSetupEvent event) {
            SPMMain.getInstance().onInitialize();
        }
    }
    
    @EventBusSubscriber(modid = "sweet_potato", bus = EventBusSubscriber.Bus.GAME)
    public static final class Subscribers {
        @SubscribeEvent
        public static void onReloadResource(AddReloadListenerEvent event) {
            event.addListener(new MagicalEnchantmentLoader());
        }
    }

    @EventBusSubscriber(modid = "sweet_potato", bus = EventBusSubscriber.Bus.MOD,
            value = Dist.CLIENT)
    public static final class ClientSubscribers {
        @SubscribeEvent
        public static void initClient(FMLClientSetupEvent event) {
            event.enqueueWork(new SPMClient());  // 1.21: Moved item/block color registry to SPMClient.run()
        }
    }
}
