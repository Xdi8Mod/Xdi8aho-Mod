package top.xdi8.mod.firefly8.screen;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import top.xdi8.mod.firefly8.core.letters.KeyedLetter;
import top.xdi8.mod.firefly8.item.FireflyItemTags;
import top.xdi8.mod.firefly8.item.symbol.SymbolStoneBlockItem;
import top.xdi8.mod.firefly8.recipe.FireflyRecipes;
import top.xdi8.mod.firefly8.recipe.TotemRecipe;
import top.xdi8.mod.firefly8.recipe.TotemRecipeInput;
import top.xdi8.mod.firefly8.stats.FireflyStats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Xdi8TableMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess levelAccess;
    private final Level level;
    private final SimpleContainer container = new SimpleContainer(5);
    @Nullable
    private RecipeHolder<TotemRecipe> totemRecipe;

    public Xdi8TableMenu(int id, Inventory inv, ContainerLevelAccess levelAccess) {
        super(FireflyMenus.XDI8_TABLE.get(), id);
        this.level = inv.player.level();
        this.levelAccess = levelAccess;

        // MID -> UP, LEFT, DOWN, RIGHT
        this.addSlot(new TotemSlot(container, 0, 80, 35));
        this.addSlot(new Slot(container, 1, 80, 11));
        this.addSlot(new Slot(container, 2, 56, 35));
        this.addSlot(new Slot(container, 3, 80, 59));
        this.addSlot(new Slot(container, 4, 104, 35));
        // Button: 129, 36; Inventory: 8, 84
        // ButtonUnpressedTex = (176, 0)
        // ButtonPressedTex = (176, 16)
        int k;
        for (k = 0; k < 3; ++k) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inv, j + k * 9 + 9, 8 + j * 18, 84 + k * 18));
            }
        }

        for (k = 0; k < 9; ++k) {
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

    private TotemRecipeInput createRecipeInput() {
        List<KeyedLetter> letters = new ArrayList<>();
        for (int i = 1; i <= 4; ++i) {
            ItemStack stack = slots.get(i).getItem();
            if (stack.is(FireflyItemTags.SYMBOL_STONES.tagKey())) {
                if (stack.getItem() instanceof SymbolStoneBlockItem item) {
                    letters.add(item.letter());
                }
            }
        }
        return new TotemRecipeInput(this.slots.getFirst().getItem(), letters);
    }

    private void confirm(Player player) {
        TotemRecipeInput input = createRecipeInput();
        Optional<RecipeHolder<TotemRecipe>> recipeHolder;
        if (this.level instanceof ServerLevel serverLevel) {
            recipeHolder = serverLevel.recipeAccess().getRecipeFor(FireflyRecipes.TOTEM_TYPE.get(), input, serverLevel);
        } else {
            recipeHolder = Optional.empty();
        }

        if (recipeHolder.isPresent()) {
            this.totemRecipe = recipeHolder.get();
            final TotemRecipe totemRecipe = this.totemRecipe.value();
            final ItemStack assemble = totemRecipe.assemble(input, this.level.registryAccess());
            container.setItem(0, assemble);
            for (int i = 1; i < 5; i++)
                container.getItem(i).shrink(1);
            // TODO add sound
            // levelAccess.execute((l, p) -> l.playSound(null, p, SPMMain.AGROFORESTRY_TABLE_FINISH.get(), SoundSource.BLOCKS, 1.0F, l.getRandom().nextFloat() * 0.1F + 0.9F));
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

    private class TotemSlot extends Slot {
        public TotemSlot(Container container, int slot, int x, int y) {
            super(container, slot, x, y);
        }

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
    }
}
