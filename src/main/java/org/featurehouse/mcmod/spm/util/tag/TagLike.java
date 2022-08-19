package org.featurehouse.mcmod.spm.util.tag;

import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;

import java.util.Objects;

/**
 * @see com.mojang.datafixers.util.Either
 */
public sealed interface TagLike<T> {
    boolean contains(Holder<T> holder);

    static <T> TagLike<T> asTag(TagKey<T> tagKey) {
        return new AsTag<>(tagKey);
    }

    static <T> TagLike<T> asItem(T item) {
        return new AsItem<>(item);
    }

    record AsTag<T>(TagKey<T> tagKey) implements TagLike<T> {
        @Override
        public boolean contains(Holder<T> holder) {
            return holder.is(tagKey);
        }
    }

    record AsItem<T>(T item) implements TagLike<T> {
        @Override
        public boolean contains(Holder<T> holder) {
            return Objects.equals(holder.value(), item);
        }
    }
}
