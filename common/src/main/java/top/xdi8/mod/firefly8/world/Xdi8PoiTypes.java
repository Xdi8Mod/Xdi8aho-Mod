package top.xdi8.mod.firefly8.world;

import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import org.featurehouse.mcmod.spm.platform.api.reg.PlatformRegister;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import io.github.qwerty770.mcmod.spmreborn.api.InternalRegistryLogWrapper;

import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class Xdi8PoiTypes {
    public static final InternalRegistryLogWrapper LOG_WRAPPER = InternalRegistryLogWrapper.firefly8("poi_types");
    public static final Supplier<PoiType> XDI8_EXIT_PORTAL =
            register("xdi8_exit_portal", FireflyBlocks.XDI8AHO_BACK_FIRE_BLOCK, 0, 1);

    private static Supplier<PoiType> register(String id, Supplier<Block> blockSupplier,
                                                    int maxTickets, int validRange) {
        return PlatformRegister.of("firefly8").poiType(id, maxTickets, validRange, () -> blockSupplier.get().getStateDefinition()
                        .getPossibleStates()
                        .stream()
                        .collect(Collectors.toUnmodifiableSet()));
    }
}
