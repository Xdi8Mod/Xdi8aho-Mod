package top.xdi8.mod.firefly8.core.totem;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.resources.ResourceLocation;
import org.featurehouse.mcmod.spm.platform.forge.ForgeBusWrapper;
import org.jetbrains.annotations.ApiStatus;
import top.xdi8.mod.firefly8.core.totem.event.TotemRegistryEvent;

import java.util.Optional;
import java.util.function.BiConsumer;

public final class TotemAbilities {
    private static final BiMap<ResourceLocation, TotemAbility> MAP = HashBiMap.create();

    public static Optional<TotemAbility> byId(ResourceLocation id) {
        return Optional.ofNullable(MAP.get(id));
    }

    public static Optional<ResourceLocation> getId(TotemAbility ability) {
        return Optional.ofNullable(MAP.inverse().get(ability));
    }

    public static void forEach(BiConsumer<ResourceLocation, TotemAbility> action) {
        MAP.forEach(action);
    }

    @ApiStatus.Internal
    public static void fireRegistry(ForgeBusWrapper bus) {
        bus.fire(new TotemRegistryEvent(MAP::put));
    }

    // Default
    static {
        MAP.put(new ResourceLocation("firefly8", "xdi8"), (level, player, hand) -> {
            // Placeholder
            return Optional.empty();
        });
    }
}
