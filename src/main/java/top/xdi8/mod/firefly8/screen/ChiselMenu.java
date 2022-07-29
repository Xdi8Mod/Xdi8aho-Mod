package top.xdi8.mod.firefly8.screen;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

/** @see net.minecraft.world.inventory.StonecutterMenu */
public class ChiselMenu extends AbstractContainerMenu {
    private final BlockPos pos;

    public ChiselMenu(int id, BlockPos pos) {
        super(FireflyMenus.CHISEL.get(), id);
        this.pos = pos;
    }

    ChiselMenu(int pContainerId, Inventory ignore) {
        this(pContainerId, BlockPos.ZERO);
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return pPlayer.getLevel().isClientSide() ||
                pPlayer.distanceToSqr(Vec3.atCenterOf(pos)) > 256;
    }
}
