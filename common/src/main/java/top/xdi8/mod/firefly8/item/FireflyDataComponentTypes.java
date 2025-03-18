package top.xdi8.mod.firefly8.item;

import com.mojang.serialization.Codec;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.qwerty770.mcmod.xdi8.api.InternalRegistryLogWrapper;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

import java.util.List;

import static io.github.qwerty770.mcmod.xdi8.util.registries.RegistryHelper.componentType;

public class FireflyDataComponentTypes {
    public static final InternalRegistryLogWrapper LOG_WRAPPER = InternalRegistryLogWrapper.firefly8("data_components");
    // "Totem" tag in 1.18.2
    public static final RegistrySupplier<DataComponentType<String>> TOTEM = componentType("totem",
            () -> new DataComponentType.Builder<String>().persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());
    // "StoredItems" tag in 1.18.2
    public static final RegistrySupplier<DataComponentType<List<ItemStack>>> STORED_ITEMS = componentType("stored_items",
            () -> new DataComponentType.Builder<List<ItemStack>>().persistent(ItemStack.CODEC.listOf())
                    .networkSynchronized(ItemStack.STREAM_CODEC.apply(ByteBufCodecs.list())).cacheEncoding().build());
    // "Fireflies" tag in 1.18.2
    public static final RegistrySupplier<DataComponentType<CustomData>> FIREFLIES = componentType("fireflies",
            () -> new DataComponentType.Builder<CustomData>().persistent(CustomData.CODEC).networkSynchronized(CustomData.STREAM_CODEC).build());
}
