package io.github.qwerty770.mcmod.xdi8.api;

import io.github.qwerty770.mcmod.xdi8.util.annotation.StableApi;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("unused")
@StableApi
public class ResourceLocationTool {
    // For compatibility between 1.21 and older versions.
    public static ResourceLocation create(String namespace, String path){
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }

    public static ResourceLocation create(String location){
        return ResourceLocation.parse(location);
    }

    public static ResourceLocation withDefaultNamespace(String location){
        return ResourceLocation.withDefaultNamespace(location);
    }
}
