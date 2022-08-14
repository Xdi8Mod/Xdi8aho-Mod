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

import java.util.List;

public class IndiumChiselItem extends Item {
    public IndiumChiselItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext pContext) {
        final BlockPos clickedPos = pContext.getClickedPos();
        final Level level = pContext.getLevel();
        ItemStack stack = pContext.getItemInHand();
        Player player = pContext.getPlayer();
        if (player == null) return InteractionResult.PASS;
        stack.hurtAndBreak(1, pContext.getPlayer(), (a) -> a.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        if (level.getBlockState(clickedPos).is(SymbolStoneBlock.fromLetter(KeyedLetter.empty()))) {
            List<SymbolStoneProductionRecipe> recipeList = level.getRecipeManager().getAllRecipesFor(FireflyRecipes.PRODUCE_T.get());
            if (recipeList.isEmpty()) return InteractionResult.PASS;
            ShufflingList<KeyedLetter> list = new ShufflingList<>();
            for (SymbolStoneProductionRecipe recipe : recipeList) {
                list.add(recipe.letter(), recipe.weight());
            }
            KeyedLetter resultLetter = list.shuffle().stream().toList().get(0);
            SymbolStoneBlock resultBlock = SymbolStoneBlock.fromLetter(resultLetter);
            level.setBlock(clickedPos, resultBlock.defaultBlockState(), 4);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
