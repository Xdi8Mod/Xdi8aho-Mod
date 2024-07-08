package org.featurehouse.mcmod.spm.platform.api.resource;

import net.minecraft.resources.ResourceLocation;

public interface KeyedReloadListener extends InternalReloadListener {
    ResourceLocation getId();
}
