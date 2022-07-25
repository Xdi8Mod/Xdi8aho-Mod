package top.xdi8.mod.firefly8.ext;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;

public interface IServerPlayerWithHiddenInventory extends IPlayerWithHiddenInventory {
    Logger LOGGER = LogUtils.getLogger();
    static IServerPlayerWithHiddenInventory xdi8$extend(ServerPlayer player) { return (IServerPlayerWithHiddenInventory) player; }
    default ServerPlayer xdi8$self() { return (ServerPlayer) this; }

    void xdi8$setPortal(ResourceKey<Level> dimension, BlockPos pos);
    Pair<ResourceKey<Level>, BlockPos> xdi8$getPortal();

    boolean xdi8$moveItemsToPortal();

    boolean xdi8$validatePortal();

    boolean xdi8$validatePortal(ResourceKey<Level> dimension, BlockPos pos);
}
