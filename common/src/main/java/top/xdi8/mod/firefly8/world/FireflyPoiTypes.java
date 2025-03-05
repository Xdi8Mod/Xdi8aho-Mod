package top.xdi8.mod.firefly8.world;

import dev.architectury.registry.registries.RegistrySupplier;
import io.github.qwerty770.mcmod.xdi8.util.registries.RegistryHelper;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import io.github.qwerty770.mcmod.xdi8.api.InternalRegistryLogWrapper;

import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class FireflyPoiTypes {
    public static final InternalRegistryLogWrapper LOG_WRAPPER = InternalRegistryLogWrapper.firefly8("poi_types");
    public static final RegistrySupplier<PoiType> XDI8_PORTAL =
            register("xdi8_portal", FireflyBlocks.XDI8AHO_PORTAL_CORE_BLOCK, 0, 1);
    public static final RegistrySupplier<PoiType> XDI8_BACK_PORTAL =
            register("xdi8_back_portal", FireflyBlocks.XDI8AHO_BACK_FIRE_BLOCK, 0, 1);

    @SuppressWarnings("SameParameterValue")
    private static RegistrySupplier<PoiType> register(String id, Supplier<Block> blockSupplier,
                                              int maxTickets, int validRange) {
        return RegistryHelper.poiType(id, maxTickets, validRange, () -> blockSupplier.get().getStateDefinition()
                        .getPossibleStates()
                        .stream()
                        .collect(Collectors.toUnmodifiableSet()));
    }
}
