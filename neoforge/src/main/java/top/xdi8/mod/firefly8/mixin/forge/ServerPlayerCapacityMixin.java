package top.xdi8.mod.firefly8.mixin.forge;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.common.util.LazyOptional;
import net.neoforged.items.CapabilityItemHandler;
import net.neoforged.items.IItemHandler;
import org.spongepowered.asm.mixin.Mixin;
import top.xdi8.mod.firefly8.ext.IServerPlayerWithHiddenInventory;
import top.xdi8.mod.firefly8.world.Xdi8DimensionUtils;

@Mixin(ServerPlayer.class)
abstract class ServerPlayerCapacityMixin extends Player implements IServerPlayerWithHiddenInventory {
    @SuppressWarnings("all")
    private ServerPlayerCapacityMixin() {
        super(null, null, 0, null);
    }

    @Override
    public boolean xdi8$moveItemsToPortal() {
        if (!Xdi8DimensionUtils.canRedirectRespawn(getLevel())) return false;
        if (!xdi8$validatePortal()) return false;
        final SimpleContainer inv = this.xdi8$getPortalInv();
        xdi8$dropOldThings(inv);
        final LazyOptional<IItemHandler> capability = this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        capability.ifPresent(itemHandler -> {
            for (int i = 0; i < itemHandler.getSlots(); i++) {
                final ItemStack itemStack = itemHandler.extractItem(i, Integer.MAX_VALUE, false);
                if (!itemStack.isEmpty()) {
                    inv.addItem(itemStack);
                }
            }
        });
        return true;
    }
}
