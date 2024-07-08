package top.xdi8.mod.firefly8.world;

import com.mojang.logging.LogUtils;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import top.xdi8.mod.firefly8.ext.IServerPlayerWithHiddenInventory;
import top.xdi8.mod.firefly8.stats.FireflyStats;

public class Xdi8DimensionUtils {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final ResourceLocation DIM_LOCATION = new ResourceLocation("firefly8", "xdi8aho");
    public static final ResourceKey<Level> XDI8AHO_DIM_KEY =
            ResourceKey.create(Registry.DIMENSION_REGISTRY, DIM_LOCATION);

    public static void teleportPlayerToXdi8aho(ServerLevel oldLevel, ServerPlayer entity, BlockPos portalPos) {
        var dim = oldLevel.getServer().getLevel(Xdi8DimensionUtils.XDI8AHO_DIM_KEY);
        if (dim != null) {
            final ServerPlayer serverPlayer = (ServerPlayer) changeDimension(entity, dim, new Xdi8TeleporterImpl(oldLevel));
            IServerPlayerWithHiddenInventory ext = IServerPlayerWithHiddenInventory.xdi8$extend(serverPlayer);
            BlockPos thatPos = portalPos;
            for (int i = 1; i < 16; i++) {
                thatPos = thatPos.above();
                if (oldLevel.getBlockState(thatPos).is(FireflyBlocks.XDI8AHO_PORTAL_TOP_BLOCK.get())) {
                    ext.xdi8$setPortal(oldLevel.dimension(), thatPos);
                    break;
                }
            }
            serverPlayer.awardStat(FireflyStats.O2X_PORTALS_ENTERED.get());
        } else {
            logError();
        }
    }

    public static void teleportToXdi8aho(ServerLevel oldLevel, Entity entity) {
        var dim = oldLevel.getServer().getLevel(Xdi8DimensionUtils.XDI8AHO_DIM_KEY);
        if (dim != null && !entity.isPassenger() && !entity.isVehicle() && entity.canChangeDimensions()) {
            changeDimension(entity, dim, new Xdi8TeleporterImpl(oldLevel));
        } else {
            logError();
        }
    }

    public static boolean canRedirectRespawn(Level level) {
        // TODO: Fix the bug
        // return DIM_LOCATION.equals(level.dimension().location());
        return false;
    }

    private static void logError(){
        LOGGER.error("Can't find dimension {} in current server", XDI8AHO_DIM_KEY);
    }

    @SuppressWarnings("unused")
    @ExpectPlatform
    static Entity changeDimension(Entity e, ServerLevel xdi8Level, TeleportWrapper teleporter) {
        throw new AssertionError();
    }
}
