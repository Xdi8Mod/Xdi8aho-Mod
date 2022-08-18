package top.xdi8.mod.firefly8.stats;

import net.minecraft.resources.ResourceLocation;
import org.featurehouse.mcmod.spm.platform.api.reg.PlatformRegister;
import top.xdi8.mod.firefly8.util.InternalRegistryLogWrapper;

import java.util.function.Supplier;

public final class FireflyStats {
    private static final PlatformRegister reg = PlatformRegister.of("firefly8");
    public static final InternalRegistryLogWrapper LOG_WRAPPER = InternalRegistryLogWrapper.firefly8("stats");

    public static final Supplier<ResourceLocation>
            INTERACT_WITH_XDI8_TABLE = reg.customStat("interact_with_xdi8_table");
    public static final Supplier<ResourceLocation> INTERACT_WITH_CHISEL = reg.customStat("interact_with_chisel");
    public static final Supplier<ResourceLocation> SYMBOL_STONES_CARVED = reg.customStat("symbol_stones_carved");
    public static final Supplier<ResourceLocation>//O2X_PORTALS_ACTIVATED = createStat("o2x_portals_activated"),
            O2X_PORTALS_ENTERED = reg.customStat("o2x_portals_entered");
    public static final Supplier<ResourceLocation> X2O_PORTALS_ENTERED = reg.customStat("x2o_portals_entered");
    public static final Supplier<ResourceLocation> FAKE_DEAD = reg.customStat("fake_dead_from_xdi8");
    public static final Supplier<ResourceLocation> INDIUM_NUGGETS_DROPPED = reg.customStat("indium_nuggets_dropped");
    public static final Supplier<ResourceLocation> TOTEMS_ENCHANTED = reg.customStat("totems_enchanted");
    public static final Supplier<ResourceLocation> FIREFLIES_CAUGHT = reg.customStat("fireflies_caught");
    public static final Supplier<ResourceLocation> FIREFLIES_RELEASED = reg.customStat("fireflies_released");

}
