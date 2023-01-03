package top.xdi8.mod.firefly8.mixin.fabric;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import top.xdi8.mod.firefly8.ext.IServerPlayerWithHiddenInventory;
import top.xdi8.mod.firefly8.world.Xdi8DimensionUtils;

@Mixin(ServerPlayer.class)
abstract class ServerPlayerCapacityMixin extends Player implements IServerPlayerWithHiddenInventory {
    @Override
    public boolean xdi8$moveItemsToPortal() {
        if (!Xdi8DimensionUtils.canRedirectRespawn(getLevel())) return false;
        if (!xdi8$validatePortal()) return false;
        final SimpleContainer inv = this.xdi8$getPortalInv();
        xdi8$dropOldThings(inv);
        final int size = this.getInventory().getContainerSize();
        for (int i = 0; i < size; i++) {
            final ItemStack extracted = this.getInventory().removeItem(i, Integer.MAX_VALUE);
            if (!extracted.isEmpty())
                inv.addItem(extracted);
        }
        return true;
    }

    @SuppressWarnings("all")
    private ServerPlayerCapacityMixin() {
        super(null, null, 0, null);
    }
}
