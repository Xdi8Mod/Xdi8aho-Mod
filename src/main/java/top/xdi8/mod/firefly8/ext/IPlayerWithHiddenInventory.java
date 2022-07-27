package top.xdi8.mod.firefly8.ext;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;

public interface IPlayerWithHiddenInventory extends IPortalCooldownEntity {
    static IPlayerWithHiddenInventory xdi8$extend(Player player) { return (IPlayerWithHiddenInventory) player; }
    default Player xdi8$self() { return (Player) this; }

    SimpleContainer xdi8$getPortalInv();

    void xdi8$passPortalInv(IPlayerWithHiddenInventory player);
}
