package org.featurehouse.mcmod.spm.platform.api.reg;

import com.mojang.serialization.Codec;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.Collection;
import java.util.Set;
import java.util.function.Supplier;

public sealed interface PlatformRegister
        permits PlatformRegisterWrapper {
    static PlatformRegister of(String modId) { return new RegistryImpl(modId); }

    ResourceLocation id(String id);

    RegistrySupplier<Item> item(String id, Supplier<Item> item);

    RegistrySupplier<Block> block(String id, Supplier<Block> block);

    <E extends BlockEntity> RegistrySupplier<BlockEntityType<E>> blockEntity(String id, BlockEntityType.BlockEntitySupplier<E> supplier, Collection<Supplier<Block>> blocks);
    <T extends Recipe<?>> RegistrySupplier<RecipeType<T>> recipeType(String id);
    <S extends RecipeSerializer<?>> RegistrySupplier<S> recipeSerializer(String id, Supplier<S> serializerSupplier);
    <M extends AbstractContainerMenu> RegistrySupplier<MenuType<M>> menu(String id, MenuType.MenuSupplier<M> factory);
    <E extends Entity> RegistrySupplier<EntityType<E>> entityType(String id, Supplier<EntityType.Builder<E>> builder);

    TagKey<Item> itemTag(String id);
    TagKey<Block> blockTag(String id);

    RegistrySupplier<ResourceLocation> customStat(String id);
    RegistrySupplier<SoundEvent> sound(String id);
    <P extends ParticleType<?>> RegistrySupplier<P> particleType(String id, Supplier<P> particleTypeSup);
    RegistrySupplier<PoiType> poiType(String id,
                              int maxTickets, int validRange,
                              Supplier<Set<BlockState>> matchingStatesSup);
    /* WORLD GEN */
    <P extends TreeDecorator> RegistrySupplier<TreeDecoratorType<P>> treeDecoratorType(String id, Supplier<Codec<P>> codecGetter);

    static PlatformRegister spm() { return RegistryImpl.SPM; }
}
