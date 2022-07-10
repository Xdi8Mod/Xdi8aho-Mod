package org.featurehouse.mcmod.spm.platform.api.tag;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

import java.util.stream.Stream;

public sealed interface TagContainer<T> permits TagContainerImpl {
    static <T> TagContainer<T> of(ResourceLocation regId, TagKey<T> tagKey) {
        return TagContainerImpl.create(regId, tagKey);
    }

    Stream<T> stream();

    boolean contains(T t);
}
