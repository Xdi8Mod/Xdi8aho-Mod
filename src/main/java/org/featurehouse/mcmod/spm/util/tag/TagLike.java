package org.featurehouse.mcmod.spm.util.tag;

import com.google.common.collect.Iterators;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import org.featurehouse.mcmod.spm.platform.api.tag.TagContainer;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @see com.mojang.datafixers.util.Either
 */
public sealed interface TagLike<T> extends Iterable<T> {
    boolean contains(T t);

    @NotNull @Override
    default Iterator<T> iterator() {
        return stream().iterator();
    }

    Stream<T> stream();

    @Override
    default void forEach(Consumer<? super T> action) {
        Iterable.super.forEach(action);
    }

    static <T> TagLike<T> asTag(TagContainer<T> tagContainer) {
        return new AsTag<>(tagContainer);
    }

    static <T> TagLike<T> asTag(ResourceLocation regId, TagKey<T> tagKey) {
        return asTag(TagContainer.of(regId, tagKey));
    }

    static <T> TagLike<T> asItem(T item) {
        return new AsItem<>(item);
    }

    record AsTag<T>(TagContainer<T> tagContainer) implements TagLike<T> {
        @Override
        public boolean contains(T t) {
            return tagContainer.contains(t);
        }

        @Override
        public Stream<T> stream() {
            return tagContainer.stream();
        }

    }

    record AsItem<T>(T item) implements TagLike<T> {

        @Override
        public boolean contains(T t) {
            return Objects.equals(t, this.item);
        }

        @Override
        public @NotNull Iterator<T> iterator() {
            return Iterators.singletonIterator(item);
        }

        @Override
        public Stream<T> stream() {
            return Stream.of(item);
        }

        @Override
        public void forEach(Consumer<? super T> action) {
            action.accept(item);
        }
    }
}