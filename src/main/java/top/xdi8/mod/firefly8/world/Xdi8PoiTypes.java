package top.xdi8.mod.firefly8.world;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import top.xdi8.mod.firefly8.block.FireflyBlocks;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class Xdi8PoiTypes {
    public static final DeferredRegister<PoiType> REGISTRY =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, "firefly8");
    public static final RegistryObject<PoiType> XDI8_ENTRANCE_PORTAL =
            register("xdi8_entrance_portal", FireflyBlocks.XDI8AHO_PORTAL_BLOCK, 0, 1);
    // TODO: should be exit portal instead

    private static RegistryObject<PoiType> register(String id,
                                                    int maxTickets, int validRange,
                                                    Supplier<Set<BlockState>> matchingStatesSup) {
        final ResourceLocation location = new ResourceLocation("firefly8", id);
        return REGISTRY.register(id, () -> new PoiType(location.toString(), matchingStatesSup.get(),
                maxTickets, validRange));
    }

    private static RegistryObject<PoiType> register(String id, Supplier<Block> blockSupplier,
                                                    int maxTickets, int validRange) {
        return register(id, maxTickets, validRange,
                () -> blockSupplier.get().getStateDefinition()
                        .getPossibleStates()
                        .stream()
                        .collect(Collectors.toUnmodifiableSet()));
    }
}
