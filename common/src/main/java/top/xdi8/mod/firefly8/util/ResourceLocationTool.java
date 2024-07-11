package top.xdi8.mod.firefly8.util;

import net.minecraft.resources.ResourceLocation;

public abstract class ResourceLocationTool {
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
