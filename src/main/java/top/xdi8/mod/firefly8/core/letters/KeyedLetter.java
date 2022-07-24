package top.xdi8.mod.firefly8.core.letters;

import net.minecraft.resources.ResourceLocation;

public interface KeyedLetter {
    ResourceLocation id();
    boolean hasLowercase();
    int lowercase();

    boolean hasMiddleCase();
    int middleCase();

    boolean hasUppercase();
    int uppercase();

    default boolean isNull() { return false; }
    static KeyedLetter empty() { return EmptyLetter.INSTANCE; }
}
