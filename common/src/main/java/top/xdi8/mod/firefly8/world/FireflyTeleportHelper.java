package top.xdi8.mod.firefly8.world;

import io.github.qwerty770.mcmod.xdi8.registries.ResourceLocationTool;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.xdi8.mod.firefly8.Firefly8;
import top.xdi8.mod.firefly8.block.FireflyBlocks;

import java.util.*;
import java.util.function.Supplier;

public class FireflyTeleportHelper {
    public static final ResourceKey<Level> XDI8AHO_DIM_KEY = ResourceKey.create(Registries.DIMENSION, ResourceLocationTool.create("firefly8", "xdi8aho"));
    public static final List<Supplier<Block>> BACK_PORTAL_BLOCKS = List.of(
            () -> Blocks.REDSTONE_BLOCK,
            () -> Blocks.REDSTONE_BLOCK,
            () -> Blocks.GLOWSTONE,
            FireflyBlocks.XDI8AHO_BACK_PORTAL_CORE_BLOCK,
            FireflyBlocks.XDI8AHO_BACK_FIRE_BLOCK
    );
    private static final int REGEN_SCALE = 32;

    public static Optional<BlockPos> findPortal(BlockPos pos, ResourceKey<PoiType> poiType, WorldBorder border, ServerLevel serverLevel) {
        PoiManager poiManager = serverLevel.getPoiManager();
        poiManager.ensureLoadedAndValid(serverLevel, pos, REGEN_SCALE);
        return poiManager.getInSquare(holder -> holder.is(poiType), pos, REGEN_SCALE, PoiManager.Occupancy.ANY)
                .map(PoiRecord::getPos)
                .filter(border::isWithinBounds)
                .min(Comparator.<BlockPos>comparingDouble(blockPos2 -> blockPos2.distSqr(pos))
                        .thenComparing(Comparator.comparingInt(Vec3i::getY).reversed()));
    }

    @Nullable
    public static TeleportTransition teleportToXdi8aho(@NotNull ServerLevel serverLevel, @NotNull Entity entity) {
        ServerLevel serverLevel2 = serverLevel.getServer().getLevel(XDI8AHO_DIM_KEY);
        if (serverLevel2 == null) {
            Firefly8.LOGGER.error("Can't find dimension {} in current server!", XDI8AHO_DIM_KEY);
            return null;
        }
        WorldBorder worldBorder = serverLevel2.getWorldBorder();
        double d = DimensionType.getTeleportationScale(serverLevel.dimensionType(), serverLevel2.dimensionType());
        BlockPos blockPos2 = worldBorder.clampToBounds(entity.getX() * d, entity.getY(), entity.getZ() * d);

        Optional<BlockPos> optional = findPortal(blockPos2, FireflyPoiTypes.XDI8_BACK_PORTAL.getKey(), worldBorder, serverLevel2);
        TeleportTransition.PostTeleportTransition postTeleportTransition = TeleportTransition.PLAY_PORTAL_SOUND.then(TeleportTransition.PLACE_PORTAL_TICKET);
        BlockPos newBlockPos;
        newBlockPos = optional.map(blockPos -> blockPos.above(2)).orElseGet(() -> createBackPortal(blockPos2, serverLevel2));
        if (!serverLevel.getBlockState(newBlockPos).isAir()) serverLevel.setBlockAndUpdate(newBlockPos, Blocks.AIR.defaultBlockState());
        if (!serverLevel.getBlockState(newBlockPos.above()).isAir()) serverLevel.setBlockAndUpdate(newBlockPos.above(), Blocks.AIR.defaultBlockState());
        return new TeleportTransition(serverLevel2,
                newBlockPos.getBottomCenter(),
                Vec3.ZERO, 0.0F, 0.0F,
                Relative.union(Relative.DELTA, Relative.ROTATION), postTeleportTransition);
    }

    @NotNull
    public static TeleportTransition teleportToOverworld(@NotNull ServerLevel serverLevel, @NotNull Entity entity) {
        final ServerLevel serverLevel2 = serverLevel.getServer().overworld();
        final WorldBorder worldBorder = serverLevel2.getWorldBorder();
        final double d = DimensionType.getTeleportationScale(serverLevel.dimensionType(), serverLevel2.dimensionType());
        final BlockPos blockPos2 = worldBorder.clampToBounds(entity.getX() * d, entity.getY(), entity.getZ() * d);

        Optional<BlockPos> optional = findPortal(blockPos2, FireflyPoiTypes.XDI8_PORTAL.getKey(), worldBorder, serverLevel2);
        TeleportTransition.PostTeleportTransition postTeleportTransition = TeleportTransition.PLAY_PORTAL_SOUND.then(TeleportTransition.PLACE_PORTAL_TICKET);
        if (optional.isPresent()) {
            return new TeleportTransition(serverLevel2,
                    findSpaceNearXdi8ahoPortal(optional.get().above(), serverLevel2).getBottomCenter(),
                    Vec3.ZERO, 0.0F, 0.0F,
                    Relative.union(Relative.DELTA, Relative.ROTATION), postTeleportTransition);
        } else {
            if (entity instanceof ServerPlayer player) {
                return player.findRespawnPositionAndUseSpawnBlock(false, TeleportTransition.DO_NOTHING);
            } else {
                return new TeleportTransition(serverLevel2,
                        serverLevel2.getSharedSpawnPos().getBottomCenter(),
                        Vec3.ZERO, 0.0F, 0.0F,
                        Relative.union(Relative.DELTA, Relative.ROTATION), postTeleportTransition);
            }
        }
    }

    private static BlockPos createBackPortal(BlockPos blockPos, ServerLevel serverLevel) {
        final int minHeight = serverLevel.getMinY();
        final int maxHeight = serverLevel.getLogicalHeight() - 1;
        Optional<BlockPos> placePos = tryPlaceBackPortal(blockPos, serverLevel, minHeight, maxHeight);
        if (placePos.isPresent()) return placePos.get();
        // Traverse this 9*9 area in a spiral path, trying to create the back portal.
        int len = 1;
        while (len < 9) {
            BlockPos currentPos = blockPos.north();
            for (int i = 0; i < len; ++i) {
                placePos = tryPlaceBackPortal(currentPos, serverLevel, minHeight, maxHeight);
                if (placePos.isPresent()) return placePos.get();
                currentPos = currentPos.east();
            }
            len += 1;
            for (int i = 0; i < len; ++i) {
                placePos = tryPlaceBackPortal(currentPos, serverLevel, minHeight, maxHeight);
                if (placePos.isPresent()) return placePos.get();
                currentPos = currentPos.south();
            }
            for (int i = 0; i < len; ++i) {
                placePos = tryPlaceBackPortal(currentPos, serverLevel, minHeight, maxHeight);
                if (placePos.isPresent()) return placePos.get();
                currentPos = currentPos.west();
            }
            len += 1;
            for (int i = 0; i < len; ++i) {
                placePos = tryPlaceBackPortal(currentPos, serverLevel, minHeight, maxHeight);
                if (placePos.isPresent()) return placePos.get();
                currentPos = currentPos.north();
            }
        }

        // Force the game to create the back portal.
        final int height = Integer.max(64, Integer.min(maxHeight - 8, serverLevel.getHeight(Heightmap.Types.MOTION_BLOCKING, blockPos.getX(), blockPos.getZ())));
        BlockPos fillPos = blockPos.atY(height);
        return placeBackPortal(fillPos, serverLevel);
    }

    private static Optional<BlockPos> tryPlaceBackPortal(BlockPos blockPos, ServerLevel serverLevel, int minHeight, int maxHeight) {
        // final int height = serverLevel.getHeight(Heightmap.Types.MOTION_BLOCKING, blockPos.getX(), blockPos.getZ());
        int height = minHeight;  // Height maps cannot be used here because it has not been generated yet.
        Optional<BlockPos> fillPos = Optional.empty();
        for (int i = maxHeight; i >= minHeight; --i){
            if (!serverLevel.getBlockState(blockPos.atY(i)).isAir()) {
                height = i;
                break;
            }
        }
        int num = 0;
        for (int i = height; i <= maxHeight; ++i) {
            if (serverLevel.getBlockState(blockPos.atY(i)).canBeReplaced()) num += 1;
            else num = 0;
            if (num > 6) {
                fillPos = Optional.of(blockPos.atY(i - 6));
                break;
            }
        }

        return fillPos.map(pos -> placeBackPortal(pos, serverLevel));
    }

    @NotNull
    private static BlockPos placeBackPortal(BlockPos blockPos, ServerLevel serverLevel) {
        for (BlockPos pos : getSurroundingBlocks(blockPos)) {
            if (serverLevel.getBlockState(pos).isAir()) {
                serverLevel.setBlockAndUpdate(pos, Blocks.COBBLESTONE.defaultBlockState());
            }
        }
        for (Supplier<Block> backPortalBlock : BACK_PORTAL_BLOCKS) {
            serverLevel.destroyBlock(blockPos, true);
            serverLevel.setBlockAndUpdate(blockPos, backPortalBlock.get().defaultBlockState());
            blockPos = blockPos.above();
        }
        return blockPos;
    }

    private static BlockPos findSpaceNearXdi8ahoPortal(BlockPos blockPos, ServerLevel serverLevel) {
        List<BlockPos> SURROUNDING_BLOCKS = getSurroundingBlocks(blockPos);
        for (BlockPos pos : SURROUNDING_BLOCKS) {
            if (serverLevel.getBlockState(pos).isAir() && serverLevel.getBlockState(pos.above()).isAir()) {
                return pos;
            }
        }
        // Destroy the blocks near the portal to create an empty space.
        for (BlockPos pos : SURROUNDING_BLOCKS) {
            serverLevel.destroyBlock(pos, true);
            serverLevel.destroyBlock(pos.above(), true);
            serverLevel.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            serverLevel.setBlockAndUpdate(pos.above(), Blocks.AIR.defaultBlockState());
        }
        return blockPos.east();
    }

    private static List<BlockPos> getSurroundingBlocks(BlockPos blockPos) {
        return List.of(blockPos.east(), blockPos.south(), blockPos.west(), blockPos.north(),
                blockPos.east().south(), blockPos.east().north(), blockPos.west().south(), blockPos.west().north());
    }
}
