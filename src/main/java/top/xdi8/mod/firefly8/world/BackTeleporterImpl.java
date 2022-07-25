package top.xdi8.mod.firefly8.world;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.xdi8.mod.firefly8.ext.IServerPlayerWithHiddenInventory;

import java.util.Optional;
import java.util.function.Function;

public class BackTeleporterImpl implements ITeleporter {
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
        entity.changeDimension(dest, new BackTeleporterImpl(portalValid));
    }

    @Nullable
    @Override
    public PortalInfo getPortalInfo(Entity oldEntity, ServerLevel destWorld, Function ignore) {
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
