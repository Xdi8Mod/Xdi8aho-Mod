package top.xdi8.mod.firefly8.network;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.slf4j.Logger;
import top.xdi8.mod.firefly8.ext.IServerPlayerWithHiddenInventory;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Xdi8RespawnPackets {
    private static final ResourceLocation ID_RESPAWN =
            new ResourceLocation("firefly8", "respawn_from_xdi8");

    private static final String VER_MAJOR = "."/*0.*/, VER_MINOR = "0712";
    private static final Predicate<String> VER_VALID = s -> s.startsWith(VER_MAJOR);

    private static final SimpleChannel RESPAWN = NetworkRegistry.newSimpleChannel(
            ID_RESPAWN, () -> VER_MAJOR + VER_MINOR,
            VER_VALID, VER_VALID
    );

    public static void init() {
        RESPAWN.registerMessage(1000, RespawnHandler.class,
                (r, b) -> {}, b->RespawnHandler.INSTANCE,
                RespawnHandler::apply, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        RESPAWN.registerMessage(1001, RespawnHandler.DieIndeed.class,
                (r, b) -> {}, b->RespawnHandler.DieIndeed.INSTANCE,
                RespawnHandler.DieIndeed::apply, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }

    public static final class RespawnHandler {
        static final RespawnHandler INSTANCE = new RespawnHandler();
        private static final Logger LOGGER = LogUtils.getLogger();
        RespawnHandler() {}
        public void apply(Supplier<NetworkEvent.Context> ctxSup) {
            final ServerPlayer sender = Objects.requireNonNull(ctxSup.get().getSender(), "sender");
            final IServerPlayerWithHiddenInventory extPlayer = IServerPlayerWithHiddenInventory.xdi8$extend(sender);
            final boolean success = extPlayer.xdi8$moveItemsToPortal();
            if (success) {
                sender.server.getPlayerList().respawn(sender, true);
                LOGGER.debug("{} \"respawned\" successfully", sender.getName());
            } else {
                // TODO die indeed
            }
        }

        public static final class DieIndeed {
            static final DieIndeed INSTANCE = new DieIndeed();
            DieIndeed() {}
            public void apply(Supplier<NetworkEvent.Context> ctxSup) {
                final Minecraft mc = Minecraft.getInstance();
                LocalPlayer player = mc.player;
                if (player == null) {
                    LOGGER.error("LocalPlayer is null");
                    return;
                }
                if (player.shouldShowDeathScreen()) {
                    if (mc.level != null) {
                        mc.setScreen(new DeathScreen(null, mc.level.getLevelData().isHardcore()));
                    } else {
                        LOGGER.error("LocalLevel is null");
                    }
                } else {
                    player.respawn();
                }
            }
        }
    }
}
