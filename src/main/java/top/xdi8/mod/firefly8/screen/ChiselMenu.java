package top.xdi8.mod.firefly8.screen;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.core.letters.KeyedLetter;
import top.xdi8.mod.firefly8.item.symbol.SymbolStoneBlockItem;
import top.xdi8.mod.firefly8.recipe.FireflyRecipes;
import top.xdi8.mod.firefly8.recipe.SymbolStoneProductionRecipe;

import java.util.List;

/** @see net.minecraft.world.inventory.StonecutterMenu */
public class ChiselMenu extends AbstractContainerMenu {
    private final BlockPos pos;
    private final ContainerLevelAccess cla;
    private final Container virtualContainer = new ResultContainer();
    private final List<SymbolStoneProductionRecipe> recipes;
    private boolean isOpen = true;
    private final Runnable breakingRunnable;

    public ChiselMenu(int id, Level level, BlockPos pos, ContainerLevelAccess cla, Inventory inventory, Runnable breakingRunnable) {
        super(FireflyMenus.CHISEL.get(), id);
        this.pos = pos;
        this.cla = cla;
        this.breakingRunnable = breakingRunnable;
        virtualContainer.setItem(0, SymbolStoneBlockItem.fromLetter(KeyedLetter.empty()).getDefaultInstance());
        this.recipes = level.getRecipeManager().getRecipesFor(FireflyRecipes.PRODUCE_T.get(), virtualContainer, level);

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inventory, k, 8 + k * 18, 142));
        }
    }

    ChiselMenu(int pContainerId, Inventory inv) {
        this(pContainerId, inv.player.getLevel(), BlockPos.ZERO, ContainerLevelAccess.NULL, inv, ()->{});
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return pPlayer.getLevel().isClientSide() ||
                (isOpen && pPlayer.distanceToSqr(Vec3.atCenterOf(pos)) < 256);
    }

    @Override
    public boolean clickMenuButton(@NotNull Player pPlayer, int pId) {
        final ItemStack result = recipes.get(pId).assemble(virtualContainer);
        final BlockState state = Block.byItem(result.getItem()).defaultBlockState();
        cla.execute((level, blockPos) -> {
            level.setBlockAndUpdate(blockPos, state);
            level.playSound(null, blockPos, SoundEvents.STONE_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
            isOpen = false;
        });
        breakingRunnable.run();
        return true;
    }

    public List<SymbolStoneProductionRecipe> getRecipes() {
        return recipes;
    }
}
