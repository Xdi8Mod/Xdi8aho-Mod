package top.xdi8.mod.firefly8.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.featurehouse.mcmod.spm.platform.api.network.PlayChannel;
import top.xdi8.mod.firefly8.advancement.criteria.FireflyCriteria;
import top.xdi8.mod.firefly8.ext.IServerPlayerWithHiddenInventory;
import top.xdi8.mod.firefly8.stats.FireflyStats;

public class FireflyNetwork {
    public static final PlayChannel CHANNEL = PlayChannel.create(
            new ResourceLocation("firefly8", "main-5.0"));

    public static final int S2C_DIE_INDEED = 0x2000;
    public static final int S2C_PREPARE_RESPAWN = 0x3000;
    public static final int C2S_RESPAWN = 0x3800;

    static {
        CHANNEL.registerS2C(S2C_DIE_INDEED, env -> env.queue(() -> {
            final Minecraft minecraft = Minecraft.getInstance();
            final LocalPlayer player = minecraft.player;
            if (player != null && minecraft.level != null) {
                if (player.shouldShowDeathScreen()) {
                    minecraft.setScreen(null);
                } else {
                    player.respawn();
                }
            }
        }));
        CHANNEL.registerS2C(S2C_PREPARE_RESPAWN, env -> env.queue(() -> {
            final Minecraft minecraft = Minecraft.getInstance();
            final LocalPlayer player = minecraft.player;
            if (player != null && minecraft.level != null) {
                CHANNEL.sendC2S(C2S_RESPAWN);
                minecraft.setScreen(new ReceivingLevelScreen());
            }
        }));
        CHANNEL.registerC2S(C2S_RESPAWN, env -> env.queue(() -> {
            if (!(env.getPlayer() instanceof ServerPlayer oldPlayer)) return;
            ServerGamePacketListenerImpl connection = oldPlayer.connection;
            ServerPlayer newPlayer = connection.player = oldPlayer.server.getPlayerList().respawn(oldPlayer, true);
            final IServerPlayerWithHiddenInventory newPlayerExt =
                    IServerPlayerWithHiddenInventory.xdi8$extend(newPlayer);
            newPlayerExt.xdi8$resetCooldown();
            newPlayerExt.xdi8$passPortalInv(IServerPlayerWithHiddenInventory.xdi8$extend(oldPlayer));
            FireflyCriteria.DIE_IN_XDI8AHO.trigger(newPlayer);
            newPlayer.awardStat(FireflyStats.FAKE_DEAD.get());
        }));
    }

    public static void init() {}
}
