package top.xdi8.mod.firefly8.item.indium;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.behavior.ShufflingList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.block.symbol.SymbolStoneBlock;
import top.xdi8.mod.firefly8.core.letters.KeyedLetter;
import top.xdi8.mod.firefly8.recipe.FireflyRecipes;
import top.xdi8.mod.firefly8.recipe.SymbolStoneProductionRecipe;

import java.util.Collections;
import java.util.List;

public class IndiumChiselItem extends Item {
    public IndiumChiselItem(Properties properties) {
        super(properties);
    }

    private static final class ShuffleCache {
        @NotNull
        private static List<SymbolStoneProductionRecipe> recipeList = Collections.emptyList();
        @NotNull
        private static ShufflingList<KeyedLetter> shufflingList = new ShufflingList<>();

        synchronized static @NotNull ShufflingList<KeyedLetter> getShufflingList(List<SymbolStoneProductionRecipe> recipes) {
            if (recipeList.equals(recipes)) return shufflingList;
            recipeList = recipes;
            shufflingList = new ShufflingList<>();
            recipes.forEach(rcp -> shufflingList.add(rcp.letter(), rcp.weight()));
            return shufflingList;
        }
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext pContext) {
        final BlockPos clickedPos = pContext.getClickedPos();
        final Level level = pContext.getLevel();
        if (level.isClientSide()) return InteractionResult.SUCCESS;
        ItemStack stack = pContext.getItemInHand();
        Player player = pContext.getPlayer();
        if (player == null) return InteractionResult.PASS;
        stack.hurtAndBreak(1, pContext.getPlayer(), (a) -> a.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        if (level.getBlockState(clickedPos).is(SymbolStoneBlock.fromLetter(KeyedLetter.empty()))) {
            List<SymbolStoneProductionRecipe> recipeList = level.getRecipeManager().getAllRecipesFor(FireflyRecipes.PRODUCE_T.get());
            ShufflingList<KeyedLetter> list = ShuffleCache.getShufflingList(recipeList);
            KeyedLetter resultLetter = list.shuffle().stream().findFirst().orElseGet(KeyedLetter::empty);
            SymbolStoneBlock resultBlock = SymbolStoneBlock.fromLetter(resultLetter);
            level.setBlock(clickedPos, resultBlock.defaultBlockState(), 4);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
