package org.featurehouse.mcmod.spm.util.registries;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.featurehouse.mcmod.spm.platform.api.reg.PlatformRegister;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

///** @deprecated use {@link PlatformRegister}. */
//@Deprecated(forRemoval = true)
public interface RegistryHelper {
    static Supplier<Item> defaultItem(String id, @NotNull Supplier<Item.Properties> settings) {
        return PlatformRegister.getInstance().item(id, ()->new Item(settings.get()));
    }

    static Supplier<Item> blockItem(String id, Supplier<Block> block2, @NotNull Supplier<Item.Properties> settings) {
        final Supplier<Item> item = () -> new BlockItem(block2.get(), settings.get());
        return PlatformRegister.getInstance().item(id, item);
    }
}
