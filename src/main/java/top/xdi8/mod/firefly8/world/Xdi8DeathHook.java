package top.xdi8.mod.firefly8.world;

import net.minecraft.client.player.LocalPlayer;

public class Xdi8DeathHook {
    /** Return false if death is canceled and player is teleported. */
    public static boolean modifyIsDeathOfDying(LocalPlayer player) {
        return true;//TODO
    }
}
