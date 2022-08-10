package org.featurehouse.mcmod.spm.platform.api.reg;

import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Codec;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.datafix.fixes.References;
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
import net.minecraftforge.registries.ForgeRegistries;
import org.featurehouse.mcmod.spm.platform.forge.ForgeTagContainer;
import org.featurehouse.mcmod.spm.platform.api.tag.TagContainer;
import org.featurehouse.mcmod.spm.platform.forge.ForgeRegistryContainer;

import java.util.Collection;
import java.util.function.Supplier;

final class RegistryImpl implements PlatformRegisterWrapper {
    //static RegistryImpl getInstance() { return INSTANCE; }

    RegistryImpl(String modId) {
        this.modId = modId;
        this.registryContainer = ForgeRegistryContainer.of(modId);
    }

    private final ForgeRegistryContainer registryContainer;
    private final String modId;

    @Override
    public ResourceLocation id(String id) {
        return new ResourceLocation(modId, id);
    }

    @Override
    public Supplier<Item> item(String id, Supplier<Item> item) {
        return registryContainer.item.register(id, item);
    }

    @Override
    public Supplier<Block> block(String id, Supplier<Block> block) {
        return registryContainer.block.register(id, block);
    }

    @Override
    public <E extends BlockEntity> Supplier<BlockEntityType<E>> blockEntity(String id, BlockEntityType.BlockEntitySupplier<E> supplier, Collection<Supplier<Block>> blocks) {
        return registryContainer.blockEntity.register(id, () -> {
            ResourceLocation location = new ResourceLocation(modId, id);
            Type<?> type = Util.fetchChoiceType(References.BLOCK_ENTITY, location.toString());
            assert type != null;
            return BlockEntityType.Builder.of(supplier, blocks.stream().map(Supplier::get).toArray(Block[]::new)).build(type);
        });
    }

    @Override
    public <T extends Recipe<Container>> Supplier<RecipeType<T>> recipeType(String id) {
        var location = id(id);
        return registryContainer.recipeType.register(id, () -> new RecipeType<>() {
            @Override
            public String toString() {
                return location.toString();
            }
        });
    }

    @Override
    public <S extends RecipeSerializer<?>> Supplier<S> recipeSerializer(String id, Supplier<S> serializerSupplier) {
        return registryContainer.recipeSerializer.register(id, serializerSupplier);
    }

    @Override
    public <M extends AbstractContainerMenu> Supplier<MenuType<M>> menu(String id, MenuType.MenuSupplier<M> factory) {
        return registryContainer.menu.register(id, () -> new MenuType<>(factory));
    }

    @Override
    public <E extends Entity> Supplier<EntityType<E>> entityType(String id, Supplier<EntityType.Builder<E>> builder) {
        return registryContainer.entityType.register(id, () -> builder.get().build(id(id).toString()));
    }

    @Override
    public TagContainer<Item> itemTag(String id) {
        return ForgeTagContainer.create(ForgeRegistries.ITEMS, id(id));
    }

    @Override
    public TagContainer<Block> blockTag(String id) {
        return ForgeTagContainer.create(ForgeRegistries.BLOCKS, id(id));
    }

    @Override
    public Supplier<ResourceLocation> customStat(String id) {
        return registryContainer.stat.register(id, () -> id(id));
    }

    @Override
    public Supplier<SoundEvent> sound(String id) {
        return registryContainer.sound.register(id, () -> new SoundEvent(new ResourceLocation(id)));
    }

    @Override
    public <P extends TreeDecorator> Supplier<TreeDecoratorType<P>> treeDecoratorType(String id, Supplier<Codec<P>> codecGetter) {
        return registryContainer.treeDecoratorType.register(id, () -> new TreeDecoratorType<>(codecGetter.get()));
    }

    static final RegistryImpl SPM = new RegistryImpl(org.featurehouse.mcmod.spm.SPMMain.MODID);
}

non-sealed interface PlatformRegisterWrapper extends PlatformRegister {}
