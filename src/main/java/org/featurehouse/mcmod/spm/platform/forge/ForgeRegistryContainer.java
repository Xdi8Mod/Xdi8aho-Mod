package org.featurehouse.mcmod.spm.platform.forge;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.jetbrains.annotations.ApiStatus;

import java.util.*;

@ApiStatus.Internal
public final class ForgeRegistryContainer {
    private ForgeRegistryContainer(String modId) {
        this.modId = Objects.requireNonNull(modId);
    }

    public final String modId;

    private final List<DeferredRegister<?>> modBusRegistries = new ArrayList<>();

    public final DeferredRegister<Item> item = ofModRegistry(ForgeRegistries.ITEMS);
    public final DeferredRegister<Block> block = ofModRegistry(ForgeRegistries.BLOCKS);
    public final DeferredRegister<BlockEntityType<?>> blockEntity = ofModRegistry(ForgeRegistries.BLOCK_ENTITIES);
    public final DeferredRegister<RecipeSerializer<?>> recipeSerializer = ofModRegistry(ForgeRegistries.RECIPE_SERIALIZERS);
    public final DeferredRegister<MenuType<?>> menu = ofModRegistry(ForgeRegistries.CONTAINERS);
    public final DeferredRegister<SoundEvent> sound = ofModRegistry(ForgeRegistries.SOUND_EVENTS);
    public final DeferredRegister<EntityType<?>> entityType = ofModRegistry(ForgeRegistries.ENTITIES);

    public final DeferredRegister<ResourceLocation> stat = ofModRegistry(Registry.CUSTOM_STAT_REGISTRY);
    public final DeferredRegister<RecipeType<?>> recipeType = ofModRegistry(Registry.RECIPE_TYPE_REGISTRY);
    public final DeferredRegister<TreeDecoratorType<?>> treeDecoratorType = ofModRegistry(ForgeRegistries.TREE_DECORATOR_TYPES);

    public void subscribeModBus(IEventBus modBus) {
        for (var reg : modBusRegistries) {
            reg.register(modBus);
        }
    }

    private <T> DeferredRegister<T> ofModRegistry(ResourceKey<? extends Registry<T>> resourceKey) {
        var reg = DeferredRegister.create(resourceKey, modId);
        modBusRegistries.add(reg);
        return reg;
    }

    private <T extends IForgeRegistryEntry<T>> DeferredRegister<T> ofModRegistry(IForgeRegistry<T> forgeRegistry) {
        var reg = DeferredRegister.create(forgeRegistry, modId);
        modBusRegistries.add(reg);
        return reg;
    }

    public static ForgeRegistryContainer of(String modId) {
        return MAP.computeIfAbsent(modId, ForgeRegistryContainer::new);
    }

    public static final Map<String, ForgeRegistryContainer> MAP = new HashMap<>();

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ForgeRegistryContainer that)) return false;
        return Objects.equals(modId, that.modId);
    }

    @Override
    public int hashCode() {
        return (modId.hashCode() << 1) + 85;
    }
}
