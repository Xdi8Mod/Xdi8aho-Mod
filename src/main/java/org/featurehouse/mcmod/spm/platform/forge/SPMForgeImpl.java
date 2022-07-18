package org.featurehouse.mcmod.spm.platform.forge;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.advancement.BalancedDietHelper;
import org.featurehouse.mcmod.spm.client.SPMClient;
import org.featurehouse.mcmod.spm.resource.magicalenchantment.MagicalEnchantmentLoader;
import org.featurehouse.mcmod.spm.platform.api.client.ColorProviders;
import org.jetbrains.annotations.ApiStatus;
import top.xdi8.mod.firefly8.item.tint.advancement.AdvancementLoadingEvent;

import java.util.Map;
import java.util.function.Supplier;

import static org.featurehouse.mcmod.spm.SPMMain.MODID;

@Mod("sweet_potato")
@ApiStatus.Internal
public class SPMForgeImpl {
    private static final ResourceLocation BALANCED_DIET = new ResourceLocation("husbandry/balanced_diet");

    public static final DeferredRegister<Item> REG_ITEM = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<Block> REG_BLOCK = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<BlockEntityType<?>> REG_BLOCK_ENTITY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MODID);
    public static final DeferredRegister<RecipeSerializer<?>> REG_RECIPE_SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MODID);
    public static final DeferredRegister<MenuType<?>> REG_MENU = DeferredRegister.create(ForgeRegistries.CONTAINERS, MODID);
    public static final DeferredRegister<SoundEvent> REG_SOUND = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);

    public static final DeferredRegister<ResourceLocation> REG_STAT = DeferredRegister.create(Registry.CUSTOM_STAT_REGISTRY, MODID);
    public static final DeferredRegister<RecipeType<?>> REG_RECIPE_TYPE = DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, MODID);
    public static final DeferredRegister<TreeDecoratorType<?>> REG_TREE_DECORATOR_TYPE = DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, MODID);

    public SPMForgeImpl() {
        SPMMain.getLogger().info("SPM initializing!");  // Don't you dare delete this line!
        final IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        REG_ITEM.register(modBus);
        REG_BLOCK.register(modBus);
        REG_BLOCK_ENTITY.register(modBus);
        REG_RECIPE_SERIALIZER.register(modBus);
        REG_MENU.register(modBus);
        REG_SOUND.register(modBus);
        REG_STAT.register(modBus);
        REG_RECIPE_TYPE.register(modBus);
        REG_TREE_DECORATOR_TYPE.register(modBus);
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
