package org.featurehouse.mcmod.spm.platform.api.reg;

import com.mojang.datafixers.types.Type;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.featurehouse.mcmod.spm.platform.api.tag.TagContainer;

import java.util.Collection;
import java.util.function.Supplier;

import static org.featurehouse.mcmod.spm.SPMMain.MODID;
import static org.featurehouse.mcmod.spm.platform.forge.SPMForgeImpl.*;

final class RegistryImpl implements PlatformRegisterWrapper {
    static final RegistryImpl INSTANCE = new RegistryImpl();
    static RegistryImpl getInstance() { return INSTANCE; }
    private RegistryImpl() {}

    @Override
    public Supplier<Item> item(String id, Supplier<Item> item) {
        return REG_ITEM.register(id, item);
    }

    @Override
    public Supplier<Block> block(String id, Supplier<Block> block) {
        return REG_BLOCK.register(id, block);
    }

    @Override
    public <E extends BlockEntity> Supplier<BlockEntityType<E>> blockEntity(String id, BlockEntityType.BlockEntitySupplier<E> supplier, Collection<Supplier<Block>> blocks) {
        return REG_BLOCK_ENTITY.register(id, () -> {
            ResourceLocation location = new ResourceLocation(MODID, id);
            Type<?> type = Util.fetchChoiceType(References.BLOCK_ENTITY, location.toString());
            assert type != null;
            return BlockEntityType.Builder.of(supplier, blocks.stream().map(Supplier::get).toArray(Block[]::new)).build(type);
        });
    }

    @Override
    public <T extends Recipe<Container>> Supplier<RecipeType<T>> recipeType(String id) {
        var location = PlatformRegister.id(id);
        return REG_RECIPE_TYPE.register(id, () -> new RecipeType<>() {
            @Override
            public String toString() {
                return location.toString();
            }
        });
    }

    @Override
    public <S extends RecipeSerializer<?>> Supplier<S> recipeSerializer(String id, Supplier<S> serializerSupplier) {
        return REG_RECIPE_SERIALIZER.register(id, serializerSupplier);
    }

    @Override
    public <M extends AbstractContainerMenu> Supplier<MenuType<M>> menu(String id, MenuType.MenuSupplier<M> factory) {
        return REG_MENU.register(id, () -> new MenuType<>(factory));
    }

    @Override
    public TagContainer<Item> itemTag(String id) {
        var key = ItemTags.create(new ResourceLocation(MODID, id));
        return TagContainer.of(new ResourceLocation("item"), key);
    }

    @Override
    public Supplier<ResourceLocation> customStat(String id) {
        return REG_STAT.register(id, () -> PlatformRegister.id(id));
    }

    @Override
    public Supplier<SoundEvent> sound(String id) {
        return REG_SOUND.register(id, () -> new SoundEvent(new ResourceLocation(id)));
    }
}

non-sealed interface PlatformRegisterWrapper extends PlatformRegister {}
