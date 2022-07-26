package top.xdi8.mod.firefly8.world;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.PortalForcer;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import top.xdi8.mod.firefly8.block.FireflyBlockTags;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import top.xdi8.mod.firefly8.ext.IPortalCooldownEntity;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/** @see PortalForcer */
public class Xdi8TeleporterImpl implements ITeleporter {
    static final Logger LOGGER = LogUtils.getLogger();

    private final ServerLevel level;

    public Xdi8TeleporterImpl(ServerLevel level) {
        this.level = level;

    }

    @Nullable
    @Override
    public PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld, Function ignore) {
        WorldBorder worldBorder = destWorld.getWorldBorder();
        double scale = DimensionType.getTeleportationScale(this.level.dimensionType(), destWorld.dimensionType());
        BlockPos pos = worldBorder.clampToBounds(entity.getX() * scale, entity.getY(), entity.getZ() * scale);
        @Nullable BlockPos portalFound = findPortalAround(pos, worldBorder, destWorld)
                .or(() -> createPortal(pos, destWorld))
                .orElseGet(() -> {
                    if (level.getGameTime() % 32 == 0) {
                        // prevent full-screen warnings
                        LOGGER.warn("No xdi8 portal valid from {}, {}", level.dimension(), entity.position());
                    }
                    return null;
                });
        if (portalFound == null) return null;
        return new PortalInfo(Vec3.atBottomCenterOf(portalFound), Vec3.ZERO, entity.getYRot(), entity.getXRot());
    }

    protected Optional<BlockPos> findPortalAround(BlockPos pos, WorldBorder border, ServerLevel destWorld) {
        PoiManager poiManager = destWorld.getPoiManager();
        poiManager.ensureLoadedAndValid(destWorld, pos, REGEN_SCALE);
        var poiRecord = poiManager.getInSquare(
                        poiType -> Objects.equals(poiType, Xdi8PoiTypes.XDI8_EXIT_PORTAL.get()),
                        pos, REGEN_SCALE, PoiManager.Occupancy.ANY)
                .filter(rec -> border.isWithinBounds(rec.getPos()))
                .min(Comparator.<PoiRecord>comparingDouble(rec -> rec.getPos().distSqr(pos))
                        .thenComparingInt(rec -> rec.getPos().getY()));
        return poiRecord.map(rec -> {
            BlockPos pos1 = rec.getPos();
            destWorld.getChunkSource().addRegionTicket(TicketType.PORTAL, new ChunkPos(pos1), 3, pos1);
            return pos1;
        });
    }

    private static final List<Supplier<Block>> X2O_PORTAL_BLOCKS_V = List.of(
            () -> Blocks.REDSTONE_BLOCK,
            () -> Blocks.REDSTONE_BLOCK,
            () -> Blocks.GLOWSTONE, // TODO: replace with indium block?
            FireflyBlocks.XDI8AHO_BACK_PORTAL_CORE_BLOCK,
            FireflyBlocks.XDI8AHO_BACK_FIRE_BLOCK,
            () -> Blocks.AIR
    );

    protected Optional<BlockPos> createPortal(BlockPos blockPos, ServerLevel destWorld) {
        final int height = destWorld.getHeight(Heightmap.Types.WORLD_SURFACE, blockPos.getX(), blockPos.getZ());
        final int maxHeight = destWorld.getMaxBuildHeight() - 1;
        final List<BlockState> states = BlockPos.betweenClosedStream(blockPos.atY(height), blockPos.atY(maxHeight))
                .sorted(Comparator.comparingInt(BlockPos::getY))
                .map(destWorld::getBlockState).toList();
        final List<Boolean> canGenList = states.stream()
                .map(Xdi8TeleporterImpl::canGen).toList();
        final int len = states.size() - 6;
        for (int index = 0; index <= len; index++) {
            if (!canGenList.get(index)) continue;
            final List<Boolean> subList = canGenList.subList(index, index + 4);
            if (!subList.contains(Boolean.FALSE)) {
                if (states.get(index + 4).isAir() && states.get(index + 5).isAir()) {
                    for (int dy = 0; dy < X2O_PORTAL_BLOCKS_V.size(); dy++) {
                        BlockPos fillPos = blockPos.atY(height + index + dy);
                        destWorld.setBlockAndUpdate(fillPos, X2O_PORTAL_BLOCKS_V.get(dy).get().defaultBlockState());
                    }
                    return Optional.of(blockPos.atY(height + index + 4));
                }
            }
        }
        throw new org.apache.commons.lang3.NotImplementedException();
    }

    private static boolean canGen(BlockState state) {
        return state.isAir() || state.is(FireflyBlockTags.PORTAL_REPLACEABLE);
    }

    private static final int REGEN_SCALE = 32;
    public static final int COOLDOWN = 200;
    public static final int COOLDOWN_SHORT = 10;

    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
        final Entity entity1 = ITeleporter.super.placeEntity(entity, currentWorld, destWorld, yaw, repositionEntity);
        IPortalCooldownEntity.xdi8$extend(entity1).xdi8$resetCooldown();
        return entity1;
    }
}
