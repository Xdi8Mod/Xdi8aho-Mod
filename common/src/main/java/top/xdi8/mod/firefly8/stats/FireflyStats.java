package top.xdi8.mod.firefly8.stats;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import io.github.qwerty770.mcmod.xdi8.registries.InternalRegistryLogWrapper;

import static io.github.qwerty770.mcmod.xdi8.registries.RegistryHelper.stat;

@SuppressWarnings("unused")
public final class FireflyStats {
    public static final InternalRegistryLogWrapper LOG_WRAPPER = InternalRegistryLogWrapper.firefly8("stats");

    public static final RegistrySupplier<ResourceLocation> INTERACT_WITH_XDI8_TABLE = stat("interact_with_xdi8_table");
    public static final RegistrySupplier<ResourceLocation> SYMBOL_STONES_CARVED = stat("symbol_stones_carved");
    public static final RegistrySupplier<ResourceLocation> O2X_PORTALS_ENTERED = stat("o2x_portals_entered");
    public static final RegistrySupplier<ResourceLocation> X2O_PORTALS_ENTERED = stat("x2o_portals_entered");
    public static final RegistrySupplier<ResourceLocation> FAKE_DEAD = stat("fake_dead_from_xdi8");
    public static final RegistrySupplier<ResourceLocation> INDIUM_NUGGETS_DROPPED = stat("indium_nuggets_dropped");
    public static final RegistrySupplier<ResourceLocation> TOTEMS_ENCHANTED = stat("totems_enchanted");
    public static final RegistrySupplier<ResourceLocation> FIREFLIES_CAUGHT = stat("fireflies_caught");
    public static final RegistrySupplier<ResourceLocation> FIREFLIES_RELEASED = stat("fireflies_released");
}
