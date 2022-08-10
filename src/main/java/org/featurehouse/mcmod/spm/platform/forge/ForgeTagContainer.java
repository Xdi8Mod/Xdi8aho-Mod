package org.featurehouse.mcmod.spm.platform.forge;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.tags.ITag;
import org.featurehouse.mcmod.spm.platform.api.tag.TagContainer;

import java.util.Objects;
import java.util.stream.Stream;

public final class ForgeTagContainer<T> implements TagContainer<T> {
    private final ITag<T> tag;

    public static <T extends IForgeRegistryEntry<T>> ForgeTagContainer<T>
                create(IForgeRegistry<T> forgeRegistry, ResourceLocation id) {
        var tagManager = Objects.requireNonNull(forgeRegistry.tags());
        var tagKey = tagManager.createTagKey(id);
        return new ForgeTagContainer<>(tagManager.getTag(tagKey));
    }

    ForgeTagContainer(ITag<T> tag) {
        this.tag = tag;
    }

    @Override
    public Stream<T> stream() {
        return tag.stream();
    }

    @Override
    public boolean contains(T t) {
        return tag.contains(t);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForgeTagContainer<?> that = (ForgeTagContainer<?>) o;
        return Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag);
    }

    @Override
    public String toString() {
        return "ForgeTagContainer{" +
                "tag=" + tag +
                '}';
    }
}
