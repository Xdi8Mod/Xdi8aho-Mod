package top.xdi8.mod.firefly8.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import org.featurehouse.mcmod.spm.platform.api.network.PlayChannel;

public class FireflyNetwork {
    public static final PlayChannel CHANNEL = PlayChannel.create(
            new ResourceLocation("firefly8", "main"), 3, 0);

    public static final int S2C_DIE_INDEED = 0x2000;

    static {
        CHANNEL.registerS2C(S2C_DIE_INDEED, env -> {
            env.enqueueWork(() -> {
                final Minecraft minecraft = Minecraft.getInstance();
                final LocalPlayer player = minecraft.player;
                if (player != null && minecraft.level != null) {
                    if (player.shouldShowDeathScreen()) {
                        minecraft.setScreen(null);
                    } else {
                        player.respawn();
                    }
                }
            });
            env.markDone();
        });
    }

    public static void init() {}
}
