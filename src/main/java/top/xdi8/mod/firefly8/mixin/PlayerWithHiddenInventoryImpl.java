package top.xdi8.mod.firefly8.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.xdi8.mod.firefly8.ext.IPlayerWithHiddenInventory;

@Mixin(Player.class)
abstract class PlayerWithHiddenInventoryImpl implements IPlayerWithHiddenInventory {
    private final SimpleContainer xdi8$portalInv = new SimpleContainer(54);

    @Override
    public SimpleContainer xdi8$getPortalInv() {
        return xdi8$portalInv;
    }

    @Inject(at = @At("RETURN"), method = "readAdditionalSaveData")
    private void readNbt(CompoundTag pCompound, CallbackInfo ci) {
        if (pCompound.contains("firefly8:portalInv", 9)) {
            xdi8$portalInv.fromTag(pCompound.getList("firefly8:portalInv", 10));
        }
    }

    @Inject(at = @At("RETURN"), method = "addAdditionalSaveData")
    private void writeNbt(CompoundTag pCompound, CallbackInfo ci) {
        final ListTag tag = xdi8$portalInv.createTag();
        pCompound.put("firefly8:portalInv", tag);
    }

    @Override
    public void xdi8$passPortalInv(IPlayerWithHiddenInventory from) {
        final SimpleContainer other = from.xdi8$getPortalInv();
        if (other == xdi8$getPortalInv()) return;
        this.xdi8$portalInv.clearContent();
        other.removeAllItems().forEach(xdi8$portalInv::addItem);
    }
}
