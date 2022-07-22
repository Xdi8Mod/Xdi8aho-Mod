package top.xdi8.mod.firefly8.world;

import it.unimi.dsi.fastutil.booleans.BooleanList;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.PortalForcer;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.util.ITeleporter;
import org.jetbrains.annotations.Nullable;
import top.xdi8.mod.firefly8.block.FireflyBlockTags;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/** @see PortalForcer */
public class Xdi8TeleporterImpl implements ITeleporter {
    private final ServerLevel level;

    public Xdi8TeleporterImpl(ServerLevel level) {
        this.level = level;

    }

    @Nullable
    @Override
    public PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld, Function ignore) {
        throw new org.apache.commons.lang3.NotImplementedException();
    }

    protected Optional<BlockPos> findPortalAround(BlockPos pos, WorldBorder border) {
        PoiManager poiManager = level.getPoiManager();
        poiManager.ensureLoadedAndValid(level, pos, REGEN_SCALE);
        var poiRecord = poiManager.getInSquare(
                        poiType -> Objects.equals(poiType, Xdi8PoiTypes.XDI8_ENTRANCE_PORTAL.get()),
                        pos, REGEN_SCALE, PoiManager.Occupancy.ANY)
                .filter(rec -> border.isWithinBounds(rec.getPos()))
                .min(Comparator.<PoiRecord>comparingDouble(rec -> rec.getPos().distSqr(pos))
                        .thenComparingInt(rec -> rec.getPos().getY()));
        return poiRecord.map(rec -> {
            BlockPos pos1 = rec.getPos();
            level.getChunkSource().addRegionTicket(TicketType.PORTAL, new ChunkPos(pos1), 3, pos1);
            return pos1;
        });
    }

    protected Optional<BlockPos> createPortal(BlockPos blockPos) {
        final int height = level.getHeight(Heightmap.Types.WORLD_SURFACE, blockPos.getX(), blockPos.getZ());
        final int maxHeight = level.getMaxBuildHeight() - 1;
        final List<BlockState> states = BlockPos.betweenClosedStream(blockPos.atY(height), blockPos.atY(maxHeight))
                .sorted(Comparator.comparingInt(BlockPos::getY))
                .map(level::getBlockState).toList();
        final List<Boolean> canGenList = states.stream()
                .map(Xdi8TeleporterImpl::canGen).toList();
        final int len = states.size() - 4;
        for (int i = 0; i <= len; i++) {
            if (!canGenList.get(i)) continue;
            final List<Boolean> subList = canGenList.subList(i, i + 4);
            if (!subList.contains(Boolean.FALSE)) {
                throw new org.apache.commons.lang3.NotImplementedException("BLOCKS");//TODO: what blocks?
            }
        }
        throw new org.apache.commons.lang3.NotImplementedException();
    }

    private static boolean canGen(BlockState state) {
        return state.isAir() || state.is(FireflyBlockTags.PORTAL_REPLACEABLE);
    }

    private static final int REGEN_SCALE = 32;
}
