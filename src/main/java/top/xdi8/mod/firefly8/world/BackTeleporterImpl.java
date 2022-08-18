package top.xdi8.mod.firefly8.world;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.xdi8.mod.firefly8.ext.IServerPlayerWithHiddenInventory;
import top.xdi8.mod.firefly8.stats.FireflyStats;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class BackTeleporterImpl implements TeleportWrapper {
    private final boolean portalValid;

    BackTeleporterImpl(boolean portalValid) {
        this.portalValid = portalValid;
    }

    public static void teleport(Entity entity, MinecraftServer server) {
        ServerLevel dest = server.overworld();
        boolean portalValid = false;
        if (entity instanceof ServerPlayer serverPlayer) {
            IServerPlayerWithHiddenInventory ext = IServerPlayerWithHiddenInventory.xdi8$extend(serverPlayer);
            if (!ext.xdi8$validatePortal()) {
                final ResourceKey<Level> respawnDimension = serverPlayer.getRespawnDimension();
                final ServerLevel serverLevel = server.getLevel(respawnDimension);
                if (serverLevel != null) {
                    dest = serverLevel;
                }
            } else {
                var key = ext.xdi8$getPortal().getLeft();
                var level = server.getLevel(key);
                if (level != null) {
                    dest = level;
                    portalValid = true;
                }
            }
        }
        Entity destEntity = Xdi8DimensionUtils.changeDimension(entity, dest, new BackTeleporterImpl(portalValid));
        if (destEntity instanceof ServerPlayer player) {
            player.awardStat(FireflyStats.X2O_PORTALS_ENTERED.get());
        }
    }

    @Nullable
    //@Override
    public PortalInfo getPortalInfo(Entity oldEntity, ServerLevel destWorld) {
        if (!(oldEntity instanceof ServerPlayer serverPlayer))
            return ofOrdinaryEntities(oldEntity, destWorld);
        IServerPlayerWithHiddenInventory ext = IServerPlayerWithHiddenInventory.xdi8$extend(serverPlayer);
        BlockPos pos;
        if (portalValid) {
            final Pair<ResourceKey<Level>, BlockPos> level2pos = ext.xdi8$getPortal();
            pos = level2pos.getRight().above();
        } else {
            pos = Optional.ofNullable(serverPlayer.getRespawnPosition())
                    .orElse(destWorld.getHeightmapPos(
                            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, destWorld.getSharedSpawnPos()));
        }
        final List<BlockState> p2s = BlockPos.betweenClosedStream(pos, pos.atY(destWorld.getLogicalHeight() - 1))
                .sorted(Comparator.comparingInt(BlockPos::getY))
                .map(destWorld::getBlockState)
                .toList();
        for (int y = pos.getY(), i = 0; y < destWorld.getLogicalHeight(); y++, i++) {
            if (p2s.get(i).isAir() && p2s.get(i + 1).isAir()) {
                pos = pos.atY(y);
                break;
            }
        }
        return new PortalInfo(Vec3.atBottomCenterOf(pos),
                oldEntity.getDeltaMovement(), oldEntity.getYRot(), oldEntity.getXRot());
    }

    private @NotNull PortalInfo ofOrdinaryEntities(Entity oldEntity, ServerLevel destWorld) {
        BlockPos pos = destWorld.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                destWorld.getSharedSpawnPos());
        return new PortalInfo(Vec3.atBottomCenterOf(pos),
                oldEntity.getDeltaMovement(), oldEntity.getYRot(), oldEntity.getXRot());
    }
}
