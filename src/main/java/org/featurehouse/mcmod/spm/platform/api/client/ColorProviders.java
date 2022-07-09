package org.featurehouse.mcmod.spm.platform.api.client;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class ColorProviders<T, P> {
    private final Map<Supplier<T>, P> map = new LinkedHashMap<>();

    public void register(Supplier<T> objSup, P provider) {
        map.put(objSup, provider);
    }

    public void register(P provider, Collection<Supplier<T>> suppliers) {
        for (Supplier<T> t : suppliers) {
            register(t, provider);
        }
    }

    public Map<Supplier<T>, P> view() { return Collections.unmodifiableMap(map); }

    private static final ColorProviders<Item, ItemColor> ITEM = new ColorProviders<>();
    private static final ColorProviders<Block, BlockColor> BLOCK = new ColorProviders<>();
    public static ColorProviders<Item, ItemColor> getItem() { return ITEM; }
    public static ColorProviders<Block, BlockColor> getBlock() { return BLOCK; }
}
