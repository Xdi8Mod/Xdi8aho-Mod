package org.featurehouse.mcmod.spm.platform.forge;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.advancement.BalancedDietHelper;
import org.featurehouse.mcmod.spm.client.SPMClient;
import org.featurehouse.mcmod.spm.platform.api.client.ColorProviders;
import org.featurehouse.mcmod.spm.resource.magicalenchantment.MagicalEnchantmentLoader;
import org.jetbrains.annotations.ApiStatus;
import top.xdi8.mod.firefly8.advancement.AdvancementLoadingEvent;

import java.util.Map;
import java.util.function.Supplier;

@Mod("sweet_potato")
@ApiStatus.Internal
public class SPMForgeImpl {
    private static final ResourceLocation BALANCED_DIET = new ResourceLocation("husbandry/balanced_diet");

    public SPMForgeImpl() {
        SPMMain.getLogger().info("SPM initializing!");  // Don't you dare delete this line!
        final IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ForgeRegistryContainer.of(SPMMain.MODID).subscribeModBus(modBus);
    }

    @Mod.EventBusSubscriber(modid = "sweet_potato", bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class ModBusSubscribers {
        @SubscribeEvent
        public static void startup(FMLCommonSetupEvent event) {
            SPMMain.getInstance().onInitialize();
        }
    }
    
    @Mod.EventBusSubscriber(modid = "sweet_potato", bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static final class Subscribers {
        @SubscribeEvent
        public static void loadingAdvancement(AdvancementLoadingEvent event) {
            if (BALANCED_DIET.equals(event.getId())) {
                BalancedDietHelper.setupCriteria(event.asContext());
            }
        }

        @SubscribeEvent
        public static void onReloadResource(AddReloadListenerEvent event) {
            event.addListener(new MagicalEnchantmentLoader());
        }
    }

    @Mod.EventBusSubscriber(modid = "sweet_potato", bus = Mod.EventBusSubscriber.Bus.MOD,
            value = Dist.CLIENT)
    public static final class ClientSubscribers {
        @SubscribeEvent
        public static void initClient(FMLClientSetupEvent event) {
            event.enqueueWork(new SPMClient()::onInitializeClient);
        }

        @SubscribeEvent
        public static void provideItemColors(ColorHandlerEvent.Item event) {
            SPMClient.initColorProviders();
            final Map<Supplier<Item>, ItemColor> view = ColorProviders.getItem().view();
            SPMMain.getLogger().debug("ItemView: {}", view);
            view.forEach((itemSupplier, itemColor) ->
                    event.getItemColors().register(itemColor, itemSupplier.get()));
        }

        @SubscribeEvent
        public static void provideBlockColors(ColorHandlerEvent.Block event) {
            SPMClient.initColorProviders();
            final Map<Supplier<Block>, BlockColor> view = ColorProviders.getBlock().view();
            view.forEach((blockSupplier, blockColor) ->
                    event.getBlockColors().register(blockColor, blockSupplier.get()));
        }
    }
}
