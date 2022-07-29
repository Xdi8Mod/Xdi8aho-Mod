package top.xdi8.mod.firefly8.world;

import com.mojang.logging.LogUtils;
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

import java.util.Collection;
import java.util.Collections;

public class Xdi8DimensionUtils {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final ResourceLocation P_LOCATION = new ResourceLocation("firefly8", "xdi8aho");
    public static final ResourceKey<Level> XDI8AHO_DIM_KEY =
            ResourceKey.create(Registry.DIMENSION_REGISTRY, P_LOCATION);

    public static void teleportToXdi8aho(ServerLevel oldLevel, Entity entity, BlockPos portalPos) {
        var dim = oldLevel.getServer().getLevel(Xdi8DimensionUtils.XDI8AHO_DIM_KEY);
        if (dim != null) {
            final Entity e = entity.changeDimension(dim, new Xdi8TeleporterImpl(oldLevel));
            if (e != null) {
                if (e instanceof ServerPlayer serverPlayer) {
                    IServerPlayerWithHiddenInventory ext = IServerPlayerWithHiddenInventory.xdi8$extend(serverPlayer);
                    //ext.xdi8$setPortal(oldLevel.dimension(), portalPos);
                    BlockPos thatPos = portalPos;
                    for (int i = 1; i < 16; i++) {
                        thatPos = thatPos.above();
                        if (oldLevel.getBlockState(thatPos).is(FireflyBlocks.XDI8AHO_PORTAL_TOP_BLOCK.get())) {
                            ext.xdi8$setPortal(oldLevel.dimension(), thatPos);
                            break;
                        }
                    }
                    serverPlayer.awardStat(FireflyStats.O2X_PORTALS_ENTERED.get());
                }
            }
        }
        else
            LOGGER.error("Can't find dimension {} in current server", XDI8AHO_DIM_KEY);
    }

    static final Collection<ResourceLocation> SPECIAL_RESPAWN =
            Collections.singleton(P_LOCATION);

    public static boolean canRedirectRespawn(Level level) {
        return SPECIAL_RESPAWN.contains(level.dimension().location());
    }
}
