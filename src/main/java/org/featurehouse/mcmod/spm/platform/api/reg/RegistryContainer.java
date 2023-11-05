package org.featurehouse.mcmod.spm.platform.api.reg;

import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.ApiStatus;

import java.util.*;

@ApiStatus.Internal
public final class RegistryContainer {
    private RegistryContainer(String modId) {
        this.modId = Objects.requireNonNull(modId);

        block = ofModRegistry(Registry.BLOCK_REGISTRY);
        item = ofModRegistry(Registry.ITEM_REGISTRY);
        blockEntity = ofModRegistry(Registry.BLOCK_ENTITY_TYPE_REGISTRY);
        recipeSerializer = ofModRegistry(Registry.RECIPE_SERIALIZER_REGISTRY);
        menu = ofModRegistry(Registry.MENU_REGISTRY);
        sound = ofModRegistry(Registry.SOUND_EVENT_REGISTRY);
        particleType = ofModRegistry(Registry.PARTICLE_TYPE_REGISTRY);
        entityType = ofModRegistry(Registry.ENTITY_TYPE_REGISTRY);
        stat = ofModRegistry(Registry.CUSTOM_STAT_REGISTRY);
        recipeType = ofModRegistry(Registry.RECIPE_TYPE_REGISTRY);
        treeDecoratorType = ofModRegistry(Registry.TREE_DECORATOR_TYPE_REGISTRY);
        poiType = ofModRegistry(Registry.POINT_OF_INTEREST_TYPE_REGISTRY);
    }

    public final String modId;

    private final List<DeferredRegister<?>> modBusRegistries = new ArrayList<>();

    public final DeferredRegister<Block> block;
    public final DeferredRegister<Item> item;
    public final DeferredRegister<BlockEntityType<?>> blockEntity;
    public final DeferredRegister<RecipeSerializer<?>> recipeSerializer;
    public final DeferredRegister<MenuType<?>> menu;
    public final DeferredRegister<SoundEvent> sound;
    public final DeferredRegister<ParticleType<?>> particleType;
    public final DeferredRegister<EntityType<?>> entityType;

    public final DeferredRegister<ResourceLocation> stat;
    public final DeferredRegister<RecipeType<?>> recipeType;
    public final DeferredRegister<TreeDecoratorType<?>> treeDecoratorType;
    public final DeferredRegister<PoiType> poiType;

    public void subscribeModBus() {
        for (var reg : modBusRegistries) {
            reg.register();
        }
    }

    private <T> DeferredRegister<T> ofModRegistry(ResourceKey<Registry<T>> resourceKey) {
        var reg = DeferredRegister.create(modId, resourceKey);
        modBusRegistries.add(reg);
        return reg;
    }

    public static RegistryContainer of(String modId) {
        return MAP.computeIfAbsent(modId, RegistryContainer::new);
    }

    public static final Map<String, RegistryContainer> MAP = new HashMap<>();

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof RegistryContainer that)) return false;
        return Objects.equals(modId, that.modId);
    }

    @Override
    public int hashCode() {
        return (modId.hashCode() << 1) + 85;
    }
}
