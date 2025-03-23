package top.xdi8.mod.firefly8.world;

import com.google.common.collect.ImmutableSet;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import io.github.qwerty770.mcmod.xdi8.api.InternalRegistryLogWrapper;

import java.util.Set;
import java.util.function.Supplier;

import static io.github.qwerty770.mcmod.xdi8.util.registries.RegistryHelper.poiType;

public final class FireflyPoiTypes {
    public static final InternalRegistryLogWrapper LOG_WRAPPER = InternalRegistryLogWrapper.firefly8("poi_types");
    public static final RegistrySupplier<PoiType> XDI8_PORTAL =
            poiType("xdi8_portal", 0, 1, () -> getBlockStates(FireflyBlocks.XDI8AHO_PORTAL_CORE_BLOCK));
    public static final RegistrySupplier<PoiType> XDI8_BACK_PORTAL =
            poiType("xdi8_back_portal", 0, 1, () -> getBlockStates(FireflyBlocks.XDI8AHO_BACK_PORTAL_CORE_BLOCK));

    private static Set<BlockState> getBlockStates(Supplier<Block> blockSupplier) {
        return ImmutableSet.copyOf(blockSupplier.get().getStateDefinition().getPossibleStates());
    }
}
