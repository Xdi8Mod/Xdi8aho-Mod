package io.github.qwerty770.mcmod.xdi8.registries;

import io.github.qwerty770.mcmod.xdi8.annotation.StableApi;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("unused")
@StableApi(since = "1.21.4-3.0.0-beta1")
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
