package org.featurehouse.mcmod.spm.platform.api.tag;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryManager;
import net.minecraftforge.registries.tags.ITag;
import net.minecraftforge.registries.tags.ITagManager;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

record TagContainerImpl<T extends IForgeRegistryEntry<T>>(ITagManager<T> tagManager,
                                                                 TagKey<T> tagKey) implements TagContainer<T> {
    static <T> TagContainer<T> create(ResourceLocation regId, TagKey<T> tagKey) {
        ForgeRegistry<? extends IForgeRegistryEntry<?>> reg = RegistryManager.ACTIVE.getRegistry(regId);
        final ITagManager<? extends IForgeRegistryEntry<?>> tags = reg.tags();
        return create0(tags, tagKey);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static <V> TagContainer<V> create0(ITagManager<?> tags, TagKey<V> tagKey) {
        TagContainerImpl t = new TagContainerImpl(tags, tagKey);
        return (TagContainer<V>) t;
    }

    @NotNull ITag<T> entries() {
        return tagManager.getTag(tagKey);
    }

    public Stream<T> stream() {
        return entries().stream();
    }

    public boolean contains(T t) {
        return entries().contains(t);
    }

    @Override
    public String toString() {
        return "TagContainer[" + tagManager
                + '/' + tagKey.location() + ']';
    }
}