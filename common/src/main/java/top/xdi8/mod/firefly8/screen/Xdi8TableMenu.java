package top.xdi8.mod.firefly8.screen;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.featurehouse.mcmod.spm.SPMMain;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.xdi8.mod.firefly8.advancement.criteria.FireflyCriteria;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import top.xdi8.mod.firefly8.item.FireflyItemTags;
import top.xdi8.mod.firefly8.recipe.FireflyRecipes;
import top.xdi8.mod.firefly8.recipe.TotemRecipe;
import top.xdi8.mod.firefly8.stats.FireflyStats;

import java.util.Collections;
import java.util.Optional;

public class Xdi8TableMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess levelAccess;
    private final Inventory inventory;
    private final Level level;
    private final SimpleContainer container = new SimpleContainer(5);
    @Nullable
    private TotemRecipe totemRecipe;

    public Xdi8TableMenu(int id, Inventory inv, ContainerLevelAccess levelAccess) {
        super(FireflyMenus.XDI8_TABLE.get(), id);
        this.inventory = inv;
        this.level = inv.player.getLevel();
        this.levelAccess = levelAccess;

        // MID -> UP, LEFT, DOWN, RIGHT
        this.addSlot(new Slot(container, 0, 80, 35) {
            @Override
            public void onTake(@NotNull Player pPlayer, @NotNull ItemStack pStack) {
                if (totemRecipe != null) {
                    pPlayer.awardRecipes(Collections.singleton(totemRecipe));
                }
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
        this.addSlot(new Slot(container, 1, 80, 11));
        this.addSlot(new Slot(container, 2, 56, 35));
        this.addSlot(new Slot(container, 3, 80, 59));
        this.addSlot(new Slot(container, 4, 104, 35));
        // Button: 129, 36; Inventory: 8, 84
        // ButtonUnpressedTex = (176, 0)
        // ButtonPressedTex = (176, 16)
        int k;
        for(k = 0; k < 3; ++k) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inv, j + k * 9 + 9, 8 + j * 18, 84 + k * 18));
            }
        }

        for(k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inv, k, 8 + k * 18, 142));
        }
    }

    Xdi8TableMenu(int id, Inventory inv) {
        this(id, inv, ContainerLevelAccess.NULL);
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return stillValid(levelAccess, pPlayer, FireflyBlocks.XDI8_TABLE.get());
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player pPlayer, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (slot.hasItem()) {
            ItemStack slotItem = slot.getItem();
            stack = slotItem.copy();
            if (index > 4) {    // in player inventory
                if (slotItem.is(FireflyItemTags.TOTEM.tagKey())) {
                    if (!this.moveItemStackTo(slotItem, 0, 1, false))
                        return ItemStack.EMPTY;
                } else if (slotItem.is(FireflyItemTags.SYMBOL_STONES.tagKey())) {
                    if (!this.moveItemStackTo(slotItem, 1, 5, false))
                        return ItemStack.EMPTY;
                } else if (index < 32) {    // main inv
                    if (!this.moveItemStackTo(slotItem, 32, 41, false))
                        return ItemStack.EMPTY;
                } else if (index < 41) {
                    if (!this.moveItemStackTo(slotItem, 5, 32, false))
                        return ItemStack.EMPTY;
                }
            } else {
                if (!this.moveItemStackTo(slotItem, 5, 41, true))
                    return ItemStack.EMPTY;
                slot.onQuickCraft(slotItem, stack);
            }
            if (slotItem.isEmpty())
                slot.set(ItemStack.EMPTY);
            else slot.setChanged();
            if (slotItem.getCount() == stack.getCount())
                return ItemStack.EMPTY;

            slot.onTake(pPlayer, slotItem);
        }
        return stack;
    }

    private void confirm(Player player) {
        final Optional<TotemRecipe> recipe = this.level.getRecipeManager().getRecipeFor(FireflyRecipes.TOTEM_T.get(), container, level);
        if (recipe.isPresent()) {
            //container.clearContent();
            final TotemRecipe totemRecipe = recipe.get();
            final ItemStack assemble = totemRecipe.assemble(container);
            this.totemRecipe = totemRecipe;
            container.setItem(0, assemble);
            for (int i = 1; i < 5; i++)
                container.getItem(i).shrink(1);
            levelAccess.execute((l, p) ->
                    l.playSound(null, p, SPMMain.AGROFORESTRY_TABLE_FINISH.get(),
                            SoundSource.BLOCKS, 1.0F, l.getRandom().nextFloat() * 0.1F + 0.9F));
            if (!player.getLevel().isClientSide()) {
                FireflyCriteria.GET_XDI8_TOTEM.trigger((ServerPlayer) player, assemble);
            }
            player.awardStat(FireflyStats.TOTEMS_ENCHANTED.get());
            broadcastChanges();
        }
    }

    @Override
    public boolean clickMenuButton(@NotNull Player player, int index) {
        if (index == 0) {
            this.confirm(player);
            return true;
        }
        return false;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public SimpleContainer getContainer() {
        return container;
    }
}
