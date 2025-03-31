package top.xdi8.mod.firefly8.core.letters;

import io.github.qwerty770.mcmod.xdi8.registries.ResourceLocationTool;
import net.minecraft.resources.ResourceLocation;

final class EmptyLetter implements KeyedLetter {
    static final ResourceLocation ID = ResourceLocationTool.create("firefly8", "none");

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public boolean hasLowercase() {
        return false;
    }

    @Override
    public int lowercase() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasMiddleCase() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int middleCase() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasUppercase() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int uppercase() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isNull() {
        return true;
    }

    @Override
    public int hashCode() {
        return 20;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof EmptyLetter;
    }

    @Override
    public String toString() {
        return "";
    }

    private EmptyLetter() {}
    static final EmptyLetter INSTANCE = new EmptyLetter();
}
