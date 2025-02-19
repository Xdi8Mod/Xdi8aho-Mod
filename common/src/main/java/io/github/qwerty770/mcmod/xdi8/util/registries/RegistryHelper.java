package io.github.qwerty770.mcmod.xdi8.util.registries;

import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Codec;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.qwerty770.mcmod.xdi8.api.ResourceLocationTool;
import io.github.qwerty770.mcmod.xdi8.util.annotation.StableApi;
import io.github.qwerty770.mcmod.xdi8.util.tag.TagContainer;
import net.minecraft.Util;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.critereon.ItemSubPredicate;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.StatFormatter;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import top.xdi8.mod.firefly8.Firefly8;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.minecraft.stats.Stats.CUSTOM;

@SuppressWarnings("unused")
@StableApi
public abstract class RegistryHelper {
    private static final List<DeferredRegister<?>> modRegistries = new ArrayList<>();
    public static final DeferredRegister<Block> blockRegistry = ofModRegistry(Registries.BLOCK);
    public static final DeferredRegister<Item> itemRegistry = ofModRegistry(Registries.ITEM);
    public static final DeferredRegister<DataComponentType<?>> dataComponentTypeRegistry = ofModRegistry(Registries.DATA_COMPONENT_TYPE);
    public static final DeferredRegister<BlockEntityType<?>> blockEntityRegistry = ofModRegistry(Registries.BLOCK_ENTITY_TYPE);
    public static final DeferredRegister<RecipeBookCategory> recipeBookCategoryRegistry = ofModRegistry(Registries.RECIPE_BOOK_CATEGORY);
    public static final DeferredRegister<RecipeDisplay.Type<?>> recipeDisplayRegistry = ofModRegistry(Registries.RECIPE_DISPLAY);
    public static final DeferredRegister<RecipeSerializer<?>> recipeSerializerRegistry = ofModRegistry(Registries.RECIPE_SERIALIZER);
    public static final DeferredRegister<RecipeType<?>> recipeTypeRegistry = ofModRegistry(Registries.RECIPE_TYPE);
    public static final DeferredRegister<MenuType<?>> menuRegistry = ofModRegistry(Registries.MENU);
    public static final DeferredRegister<SoundEvent> soundRegistry = ofModRegistry(Registries.SOUND_EVENT);
    public static final DeferredRegister<ParticleType<?>> particleTypeRegistry = ofModRegistry(Registries.PARTICLE_TYPE);
    public static final DeferredRegister<EntityType<?>> entityTypeRegistry = ofModRegistry(Registries.ENTITY_TYPE);
    public static final DeferredRegister<ResourceLocation> statRegistry = ofModRegistry(Registries.CUSTOM_STAT);
    public static final DeferredRegister<PoiType> poiTypeRegistry = ofModRegistry(Registries.POINT_OF_INTEREST_TYPE);
    public static final DeferredRegister<ItemSubPredicate.Type<?>> itemSubPredicateRegistry = ofModRegistry(Registries.ITEM_SUB_PREDICATE_TYPE);
    public static final DeferredRegister<CriterionTrigger<?>> criterionTriggerRegistry = ofModRegistry(Registries.TRIGGER_TYPE);
    public static final DeferredRegister<CreativeModeTab> creativeTabRegistry = ofModRegistry(Registries.CREATIVE_MODE_TAB);

    public static ResourceLocation id(String id) {
        return ResourceLocationTool.create(Firefly8.MODID, id);
    }

    public static ResourceKey<Block> blockId(String id) {
        return ResourceKey.create(Registries.BLOCK, id(id));
    }

    public static ResourceKey<Item> itemId(String id) {
        return ResourceKey.create(Registries.ITEM, id(id));
    }

    public static <B extends Block> RegistrySupplier<B> block(String id, Function<BlockBehaviour.Properties, B> function, BlockBehaviour.Properties properties) {
        return block(blockId(id), function, properties);
    }

    public static <B extends Block> RegistrySupplier<B> block(ResourceKey<Block> resourceKey, Function<BlockBehaviour.Properties, B> function, BlockBehaviour.Properties properties) {
        return blockRegistry.register(resourceKey.location(), () -> function.apply(properties.setId(resourceKey)));
    }

    public static RegistrySupplier<Block> defaultBlock(String id, BlockBehaviour.Properties prop) {
        return block(id, Block::new, prop);
    }

    public static <I extends Item> RegistrySupplier<I> item(String id, Function<Item.Properties, I> function, Item.Properties properties) {
        return item(itemId(id), function, properties);
    }

    public static <I extends Item> RegistrySupplier<I> item(String id, Function<Item.Properties, I> function, Supplier<Item.Properties> properties) {
        ResourceKey<Item> key = itemId(id);
        return itemRegistry.register(key.location(), () -> function.apply(properties.get().setId(key)));
    }

    public static <I extends Item> RegistrySupplier<I> item(ResourceKey<Item> resourceKey, Function<Item.Properties, I> function, Item.Properties properties) {
        return itemRegistry.register(resourceKey.location(), () -> function.apply(properties.setId(resourceKey)));
    }

    public static RegistrySupplier<Item> defaultItem(String id, Item.Properties properties) {
        return item(id, Item::new, properties);
    }

    public static RegistrySupplier<BlockItem> blockItem(String id, Supplier<Block> block2, Item.Properties properties) {
        return itemRegistry.register(id, () -> new BlockItem(block2.get(), properties.setId(itemId(id)).useBlockDescriptionPrefix()));
    }

    public static <T> RegistrySupplier<DataComponentType<T>> componentType(String id, Supplier<DataComponentType<T>> componentType){
        return dataComponentTypeRegistry.register(id, componentType);
    }

    @SafeVarargs
    public static <E extends BlockEntity> RegistrySupplier<BlockEntityType<E>> blockEntity(String id, BlockEntityType.BlockEntitySupplier<E> supplier, Supplier<Block>... blocks) {
        Type<?> type = Util.fetchChoiceType(References.BLOCK_ENTITY, id);
        assert type != null;
        return blockEntityRegistry.register(id, () -> new BlockEntityType<>(supplier, Set.copyOf(Arrays.stream(blocks).map(Supplier::get).toList())));
    }

    public static RegistrySupplier<RecipeBookCategory> recipeBookCategory(String id) {
        return recipeBookCategoryRegistry.register(id, RecipeBookCategory::new);
    }

    public static <T extends RecipeDisplay> RegistrySupplier<RecipeDisplay.Type<T>> recipeDisplay(String id, Supplier<RecipeDisplay.Type<T>> typeSupplier) {
        return recipeDisplayRegistry.register(id, typeSupplier);
    }

    public static <S extends RecipeSerializer<?>> RegistrySupplier<S> recipeSerializer(String id, Supplier<S> serializerSupplier) {
        return recipeSerializerRegistry.register(id, serializerSupplier);
    }

    public static <I extends RecipeInput, T extends Recipe<I>> RegistrySupplier<RecipeType<T>> recipeType(String id) {
        ResourceLocation id2 = id(id);
        return recipeTypeRegistry.register(id, () -> new RecipeType<>() {
            @Override
            public String toString() {
                return id2.toString();
            }
        });
    }

    public static <H extends AbstractContainerMenu> RegistrySupplier<MenuType<H>> simpleMenuType(String id, MenuType.MenuSupplier<H> factory) {
        return menuRegistry.register(id, () -> new MenuType<>(factory, FeatureFlags.VANILLA_SET));
    }

    public static <H extends AbstractContainerMenu> RegistrySupplier<MenuType<H>> extendedMenuType(String id, MenuRegistry.ExtendedMenuTypeFactory<H> factory) {
        return menuRegistry.register(id, () -> MenuRegistry.ofExtended(factory));
    }

    public static Supplier<SoundEvent> sound(String id) {
        return soundRegistry.register(id, () -> SoundEvent.createVariableRangeEvent(id(id)));
    }

    public static  <P extends ParticleType<?>> RegistrySupplier<P> particleType(String id, Supplier<P> particleTypeSup) {
        return particleTypeRegistry.register(id, particleTypeSup);
    }

    public static  <E extends Entity> RegistrySupplier<EntityType<E>> entityType(String id, Supplier<EntityType.Builder<E>> builder) {
        return entityTypeRegistry.register(id, () -> builder.get().build(ResourceKey.create(Registries.ENTITY_TYPE, id(id))));
    }

    public static TagContainer<Block> blockTag(String id) {
        return TagContainer.register(id(id), BuiltInRegistries.BLOCK);
    }

    public static TagContainer<Item> itemTag(String id) {
        return TagContainer.register(id(id), BuiltInRegistries.ITEM);
    }

    public static RegistrySupplier<ResourceLocation> stat(String id, StatFormatter statFormatter) {
        ResourceLocation id2 = id(id);
        CUSTOM.get(id2, statFormatter);
        return statRegistry.register(id, () -> id2);
    }

    public static RegistrySupplier<ResourceLocation> stat(String id) { return stat(id, StatFormatter.DEFAULT); }

    public static RegistrySupplier<PoiType> poiType(String id, int maxTickets, int validRange, Supplier<Set<BlockState>> matchingStatesSup) {
        return poiTypeRegistry.register(id, () -> new PoiType(matchingStatesSup.get(), maxTickets, validRange));
    }

    public static <T extends ItemSubPredicate> RegistrySupplier<ItemSubPredicate.Type<T>> itemSubPredicateType(String id, Codec<T> codec) {
        return itemSubPredicateRegistry.register(id, () -> new ItemSubPredicate.Type<>(codec));
    }

    public static <T extends CriterionTrigger<?>> RegistrySupplier<T> criterionTrigger(String id, Supplier<T> trigger) {
        return criterionTriggerRegistry.register(id, trigger);
    }

    public static RegistrySupplier<CreativeModeTab> creativeModeTab(String id, CreativeModeTab tab){
        return creativeTabRegistry.register(id, () -> tab);
    }

    private static <T> DeferredRegister<T> ofModRegistry(ResourceKey<Registry<T>> resourceKey) {
        var reg = DeferredRegister.create(Firefly8.MODID, resourceKey);
        modRegistries.add(reg);
        return reg;
    }

    public static void registerAll(){
        for (var reg : modRegistries) {
            reg.register();
        }
    }
}