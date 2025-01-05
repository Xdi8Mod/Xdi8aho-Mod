package top.xdi8.mod.firefly8.core.totem;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.github.qwerty770.mcmod.xdi8.api.ResourceLocationTool;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;
import top.xdi8.mod.firefly8.core.letters.event.Xdi8RegistryEvents;

import java.util.Optional;

public final class TotemAbilities {
    private static final BiMap<ResourceLocation, TotemAbility> MAP = HashBiMap.create();

    public static Optional<TotemAbility> byId(ResourceLocation id) {
        return Optional.ofNullable(MAP.get(id));
    }

    public static Optional<ResourceLocation> getId(TotemAbility ability) {
        return Optional.ofNullable(MAP.inverse().get(ability));
    }

    @ApiStatus.Internal
    public static void fireRegistry() {
        Xdi8RegistryEvents.TOTEM.invoker().accept(MAP::put);
    }

    // Default
    static {
        MAP.put(ResourceLocationTool.create("firefly8", "xdi8"), (level, player, hand) -> {
            // TODO Placeholder
            return Optional.empty();
        });
    }
}
