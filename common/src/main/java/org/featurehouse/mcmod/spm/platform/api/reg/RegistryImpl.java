package org.featurehouse.mcmod.spm.platform.api.reg;

import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Codec;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.datafix.fixes.References;
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
import top.xdi8.mod.firefly8.util.ResourceLocationTool;

import java.util.Collection;
import java.util.Set;
import java.util.function.Supplier;

public final class RegistryImpl implements PlatformRegisterWrapper {
    RegistryImpl(String modId) {
        this.modId = modId;
        this.registryContainer = RegistryContainer.of(modId);
    }

    private final RegistryContainer registryContainer;
    private final String modId;

    @Override
    public ResourceLocation id(String id) {
        return ResourceLocationTool.create(modId, id);
    }

    @Override
    public RegistrySupplier<Item> item(String id, Supplier<Item> item) {
        return registryContainer.item.register(id, item);
    }

    @Override
    public RegistrySupplier<Block> block(String id, Supplier<Block> block) {
        return registryContainer.block.register(id, block);
    }

    @Override
    public <E extends BlockEntity> RegistrySupplier<BlockEntityType<E>> blockEntity(String id, BlockEntityType.BlockEntitySupplier<E> supplier, Collection<Supplier<Block>> blocks) {
        return registryContainer.blockEntity.register(id, () -> {
            ResourceLocation location = id(id);
            Type<?> type = Util.fetchChoiceType(References.BLOCK_ENTITY, location.toString());
            assert type != null;
            return BlockEntityType.Builder.of(supplier, blocks.stream().map(Supplier::get).toArray(Block[]::new)).build(type);
        });
    }

    @Override
    public <T extends Recipe<?>> RegistrySupplier<RecipeType<T>> recipeType(String id) {
        var location = id(id);
        return registryContainer.recipeType.register(id, () -> new RecipeType<>() {
            @Override
            public String toString() {
                return location.toString();
            }
        });
    }

    @Override
    public <S extends RecipeSerializer<?>> RegistrySupplier<S> recipeSerializer(String id, Supplier<S> serializerSupplier) {
        return registryContainer.recipeSerializer.register(id, serializerSupplier);
    }

    @Override
    public <M extends AbstractContainerMenu> RegistrySupplier<MenuType<M>> menu(String id, MenuType.MenuSupplier<M> factory) {
        return registryContainer.menu.register(id, () -> new MenuType<>(factory));
    }

    @Override
    public <E extends Entity> RegistrySupplier<EntityType<E>> entityType(String id, Supplier<EntityType.Builder<E>> builder) {
        return registryContainer.entityType.register(id, () -> builder.get().build(id(id).toString()));
    }

    @Override
    public TagKey<Item> itemTag(String id) {
        return TagKey.create(Registries.ITEM, id(id));
    }

    @Override
    public TagKey<Block> blockTag(String id) {
        return TagKey.create(Registries.BLOCK, id(id));
    }

    @Override
    public RegistrySupplier<ResourceLocation> customStat(String id) {
        return registryContainer.stat.register(id, () -> id(id));
    }

    @Override
    public RegistrySupplier<SoundEvent> sound(String id) {
        return registryContainer.sound.register(id, () -> SoundEvent.createVariableRangeEvent(id(id)));
    }

    @Override
    public <P extends ParticleType<?>> RegistrySupplier<P> particleType(String id, Supplier<P> particleTypeSup) {
        return registryContainer.particleType.register(id, particleTypeSup);
    }

    @Override
    public RegistrySupplier<PoiType> poiType(String id, int maxTickets, int validRange, Supplier<Set<BlockState>> matchingStatesSup) {
        return registryContainer.poiType.register(id, () -> new PoiType(matchingStatesSup.get(), maxTickets, validRange));
    }

    @Override
    public <P extends TreeDecorator> RegistrySupplier<TreeDecoratorType<P>> treeDecoratorType(String id, Supplier<Codec<P>> codecGetter) {
        return registryContainer.treeDecoratorType.register(id, () -> new TreeDecoratorType<>(codecGetter.get()));
    }

    static final RegistryImpl SPM = new RegistryImpl(org.featurehouse.mcmod.spm.SPMMain.MODID);
}

interface PlatformRegisterWrapper extends PlatformRegister {}
