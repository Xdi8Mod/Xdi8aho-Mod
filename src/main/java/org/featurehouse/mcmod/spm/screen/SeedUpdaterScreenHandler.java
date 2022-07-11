package org.featurehouse.mcmod.spm.screen;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.recipe.SeedUpdatingRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SeedUpdaterScreenHandler extends ItemCombinerMenu {
    private final Level world; // field_25385
    @Nullable
    private SeedUpdatingRecipe recipe; // field_25386
    private final List<SeedUpdatingRecipe> list; // field_25668

    public SeedUpdaterScreenHandler(int syncId, Inventory inventory) {
        this(syncId, inventory, ContainerLevelAccess.NULL);
    }

    public SeedUpdaterScreenHandler(int syncId, Inventory inventory, ContainerLevelAccess context) {
        super(SPMMain.SEED_UPDATER_SCREEN_HANDLER_TYPE.get(), syncId, inventory, context);
        this.world = inventory.player.level;
        this.list = this.world.getRecipeManager().getAllRecipesFor(SPMMain.SEED_UPDATING_RECIPE_TYPE.get());
    }

    protected boolean isValidBlock(@NotNull BlockState state) {
        return state.is(SPMMain.SEED_UPDATER.get());
    }

    public boolean stillValid(Player player) {
        return stillValid(this.access, player, SPMMain.SEED_UPDATER.get());
    }

    @Override
    public void createResult() {
        List<SeedUpdatingRecipe> list1 = this.world.getRecipeManager().getRecipesFor(
                SPMMain.SEED_UPDATING_RECIPE_TYPE.get(), this.inputSlots, this.world
        );
        if (list1.isEmpty())
            this.resultSlots.setItem(0, ItemStack.EMPTY);
        else {
            this.recipe = list1.get(0);
            ItemStack itemStack = this.recipe.assemble(this.inputSlots);
            this.resultSlots.setRecipeUsed(recipe);
            this.resultSlots.setItem(0, itemStack);
        }
    }

    @Override
    protected boolean mayPickup(Player player, boolean present) {
        return this.recipe != null && this.recipe.matches(this.inputSlots, this.world);
    }

    @Override
    protected void onTake(Player player, @NotNull ItemStack stack) {
        //stack.onCraft(player.world, player, stack.getCount());

        this.resultSlots.awardUsedRecipes(player);
        this.putStack(0);
        this.putStack(1);
        //output.markDirty();
        this.access.execute((world1, blockPos) -> {
            /*world1.syncWorldEvent(1044, blockPos, 8844110));*/
            world1.playSound(null, blockPos, SPMMain.AGROFORESTRY_TABLE_FINISH.get(), SoundSource.BLOCKS, 1.0F, world1.getRandom().nextFloat() * 0.1F + 0.9F);
        });
        player.awardStat(SPMMain.CROP_UPGRADED.get());
        //return stack;
    }

    private void putStack(int i) {
        // method_29539
        ItemStack itemStack = this.inputSlots.getItem(i);
        itemStack.shrink(1);
        this.inputSlots.setItem(i, itemStack);
    }

    @Override
    protected boolean shouldQuickMoveToAdditionalSlot(ItemStack itemStack) {
        // shouldQuickMoveToAdditionalSlot
        return this.list.stream().anyMatch(seedUpdatingRecipe -> seedUpdatingRecipe.method_30029(itemStack));
    }

    @Override
    public void slotsChanged(Container inventory) {
        super.slotsChanged(inventory);
    }
}
