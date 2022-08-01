package top.xdi8.mod.firefly8.stats;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class FireflyStats {
    public static final DeferredRegister<ResourceLocation> REGISTRY = DeferredRegister.create(Registry.CUSTOM_STAT_REGISTRY, "firefly8");
    public static final RegistryObject<ResourceLocation>
            INTERACT_WITH_XDI8_TABLE = createStat("interact_with_xdi8_table"),
            INTERACT_WITH_CHISEL = createStat("interact_with_chisel"),
            SYMBOL_STONES_CARVED = createStat("symbol_stones_carved"),
            //O2X_PORTALS_ACTIVATED = createStat("o2x_portals_activated"),
            O2X_PORTALS_ENTERED = createStat("o2x_portals_entered"),
            X2O_PORTALS_ENTERED = createStat("x2o_portals_entered"),
            FAKE_DEAD = createStat("fake_dead_from_xdi8"),
            INDIUM_NUGGETS_DROPPED = createStat("indium_nuggets_dropped"),
            TOTEMS_ENCHANTED = createStat("totems_enchanted"),
            FIREFLIES_CAUGHT = createStat("fireflies_caught"),
            FIREFLIES_RELEASED = createStat("fireflies_released");

    private static RegistryObject<ResourceLocation> createStat(String id) {
        return REGISTRY.register(id, () -> new ResourceLocation("firefly8", id));
    }
}
