package top.xdi8.mod.firefly8.screen;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.ext.IServerPlayerWithHiddenInventory;

public class TakeOnlyChestMenu extends AbstractContainerMenu {
    private final Inventory inventory;
    private final Container container;

    TakeOnlyChestMenu(int pContainerId, Inventory inventory) {
        this(pContainerId, inventory, new SimpleContainer(54));
    }

    public TakeOnlyChestMenu(int id, Inventory inv, Container container) {
        super(FireflyMenus.TAKE_ONLY_CHEST.get(), id);
        this.inventory = inv;
        checkContainerSize(container, 54);
        this.container = container;
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 9; j++)
                this.addSlot(new TakeOnlySlot(container, j + i * 9, 8 + j * 18, 18 + i * 18));

        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(inv, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + 36));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(inv, i1, 8 + i1 * 18, 161 + 36));
        }
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return pPlayer.getLevel().isClientSide() ||
                ((IServerPlayerWithHiddenInventory) pPlayer).xdi8$validatePortal();
    }

    static final class TakeOnlySlot extends Slot {
        public TakeOnlySlot(Container pContainer, int pIndex, int pX, int pY) {
            super(pContainer, pIndex, pX, pY);
        }

        @Override
        public boolean mayPlace(@NotNull ItemStack pStack) {
            return false;
        }
    }

    public @NotNull ItemStack quickMoveStack(@NotNull Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (pIndex < 54) {
                if (!this.moveItemStackTo(itemstack1, 54, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, 54, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    public Container getContainer() {
        return container;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
