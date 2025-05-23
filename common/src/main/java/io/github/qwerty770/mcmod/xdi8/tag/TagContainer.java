package io.github.qwerty770.mcmod.xdi8.tag;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public record TagContainer<T> (Registry<T> registry, TagKey<T> tagKey) {
    public static <T> TagContainer<T> register(ResourceLocation id, Registry<T> registry) {
        var tagKey = TagKey.create(registry.key(), id);
        return new TagContainer<>(registry, tagKey);
    }

    public HolderSet<T> entries() {
        Optional<HolderSet.Named<T>> optional = registry.get(tagKey);
        return optional.isPresent() ? optional.get() : HolderSet.empty();
    }

    public Stream<T> stream() {
        return entries().stream().map(Holder::value);
    }

    public boolean contains(T t) {
        return entries().stream().anyMatch(entry -> Objects.equals(t, entry.value()));
    }

    @Override
    public String toString() {
        return "TagContainer[" + registry.key().location()
                + '/' + tagKey.location() + ']';
    }
}
