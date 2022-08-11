package org.featurehouse.mcmod.spm.platform.api.reg;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.featurehouse.mcmod.spm.platform.api.tag.TagContainer;

import java.util.Collection;
import java.util.function.Supplier;

public sealed interface PlatformRegister
        permits PlatformRegisterWrapper {
    static PlatformRegister of(String modId) { return new RegistryImpl(modId); }

    ResourceLocation id(String id);

    Supplier<Item> item(String id, Supplier<Item> item);

    Supplier<Block> block(String id, Supplier<Block> block);

    <E extends BlockEntity> Supplier<BlockEntityType<E>> blockEntity(String id, BlockEntityType.BlockEntitySupplier<E> supplier, Collection<Supplier<Block>> blocks);
    <T extends Recipe<Container>> Supplier<RecipeType<T>> recipeType(String id);
    <S extends RecipeSerializer<?>> Supplier<S> recipeSerializer(String id, Supplier<S> serializerSupplier);
    <M extends AbstractContainerMenu> Supplier<MenuType<M>> menu(String id, MenuType.MenuSupplier<M> factory);
    <E extends Entity> Supplier<EntityType<E>> entityType(String id, Supplier<EntityType.Builder<E>> builder);

    TagContainer<Item> itemTag(String id);
    TagContainer<Block> blockTag(String id);

    Supplier<ResourceLocation> customStat(String id);
    Supplier<SoundEvent> sound(String id);
    /* WORLD GEN */
    <P extends TreeDecorator> Supplier<TreeDecoratorType<P>> treeDecoratorType(String id, Supplier<Codec<P>> codecGetter);

    static PlatformRegister spm() { return RegistryImpl.SPM; }
}
