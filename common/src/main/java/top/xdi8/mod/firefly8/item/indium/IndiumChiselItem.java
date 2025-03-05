package top.xdi8.mod.firefly8.item.indium;

import io.github.qwerty770.mcmod.xdi8.util.tag.TagContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.block.symbol.SymbolStoneBlock;
import top.xdi8.mod.firefly8.core.letters.KeyedLetter;
import top.xdi8.mod.firefly8.recipe.FireflyRecipes;
import top.xdi8.mod.firefly8.recipe.SymbolStoneProductionRecipe;
import top.xdi8.mod.firefly8.stats.FireflyStats;

import java.util.*;

public class IndiumChiselItem extends Item {
    public IndiumChiselItem(Properties properties) {
        super(properties);
    }

    private static final class RecipeListCache {
        private static @NotNull List<SymbolStoneProductionRecipe> recipeList = Collections.emptyList();
        private static @NotNull List<KeyedLetter> letterList = Collections.emptyList();
        private static @NotNull Map<Block, Map<KeyedLetter, Double>> letterBonusMap = new HashMap<>();

        synchronized static @NotNull Map<Block, Map<KeyedLetter, Double>> getLetterBonusMap(List<SymbolStoneProductionRecipe> recipes) {
            if (recipeList.equals(recipes)) return letterBonusMap;
            recipeList = recipes;
            letterList = new ArrayList<>();
            letterBonusMap = new HashMap<>();
            for (SymbolStoneProductionRecipe recipe : recipes) {
                KeyedLetter letter = recipe.letter;
                if (!letterList.contains(letter)) letterList.add(letter);
                for (SymbolStoneProductionRecipe.WeightEntry entry : recipe.weight) {
                    double weight = entry.weight();
                    TagContainer<Block> tagContainer = TagContainer.register(entry.tag().location(), BuiltInRegistries.BLOCK);
                    tagContainer.stream().forEach(block -> {
                        if (letterBonusMap.containsKey(block)) {
                            Map<KeyedLetter, Double> map = letterBonusMap.get(block);
                            if (map.containsKey(letter)) map.put(letter, map.get(letter) + weight);
                            else map.put(letter, weight);
                        } else {
                            Map<KeyedLetter, Double> map = new HashMap<>();
                            map.put(letter, weight);
                            letterBonusMap.put(block, map);
                        }
                    });
                }
            }
            return letterBonusMap;
        }

        synchronized static @NotNull List<KeyedLetter> getLetterList(List<SymbolStoneProductionRecipe> recipes) {
            if (recipeList.equals(recipes)) return letterList;
            letterList = new ArrayList<>();
            for (SymbolStoneProductionRecipe recipe : recipes) {
                KeyedLetter letter = recipe.letter;
                if (!letterList.contains(letter)) letterList.add(letter);
            }
            return letterList;
        }
    }

    private static double[] softmax(double[] arr) {
        // Calculate the softmax function, also known as the normalized exponential function.
        double[] powers = new double[arr.length];
        double[] result = new double[arr.length];
        double sum = 0.0;
        for (int i = 0; i < arr.length; ++i) {
            double exp = Math.exp(arr[i]);
            powers[i] = exp;
            sum += exp;
        }
        for (int i = 0; i < arr.length; ++i) {
            result[i] = powers[i] / sum;
        }
        return result;
    }

    private static KeyedLetter getResultLetter(List<SymbolStoneProductionRecipe> recipes, ServerLevel level, BlockPos pos) {
        List<KeyedLetter> letterList = RecipeListCache.getLetterList(recipes);
        Map<Block, Map<KeyedLetter, Double>> letterBonusMap = RecipeListCache.getLetterBonusMap(recipes);
        double[] array1 = new double[letterList.size()];
        for (int i = 0; i < letterList.size(); ++i) {
            array1[i] = 1;
        }
        // Check 26 surrounding blocks
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                for (int k = -1; k <= 1; ++k) {
                    if (i == 0 && j == 0 && k == 0) continue;
                    Block block = level.getBlockState(pos.offset(i, j, k)).getBlock();
                    if (letterBonusMap.containsKey(block)) {
                        for (Map.Entry<KeyedLetter, Double> entry : letterBonusMap.get(block).entrySet()) {
                            array1[letterList.indexOf(entry.getKey())] += entry.getValue();
                        }
                    }
                }
            }
        }
        double[] array2 = softmax(array1);
        double random = level.random.nextDouble();
        double cumulated = 0.0;
        for (int i = 0; i < letterList.size(); ++i) {
            cumulated += array2[i];
            if (cumulated > random) return letterList.get(i);
        }
        return letterList.getLast();
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext pContext) {
        final BlockPos clickedPos = pContext.getClickedPos();
        final Level level = pContext.getLevel();
        if (level.isClientSide()) return InteractionResult.SUCCESS;
        ItemStack stack = pContext.getItemInHand();
        Player player = pContext.getPlayer();
        if (player == null) return InteractionResult.PASS;
        if (level.getBlockState(clickedPos).is(SymbolStoneBlock.fromLetter(KeyedLetter.empty()))) {
            stack.hurtAndBreak(1, pContext.getPlayer(), LivingEntity.getSlotForHand(pContext.getHand()));
            List<SymbolStoneProductionRecipe> recipeList = ((ServerLevel) level).recipeAccess().getRecipes().stream()
                    .filter((recipeHolder) -> recipeHolder.value().getType() == FireflyRecipes.PRODUCE_TYPE.get())
                    .map((recipeHolder) -> (SymbolStoneProductionRecipe) recipeHolder.value())
                    .toList();
            KeyedLetter resultLetter = getResultLetter(recipeList, ((ServerLevel) level), clickedPos);
            SymbolStoneBlock resultBlock = SymbolStoneBlock.fromLetter(resultLetter);
            level.setBlock(clickedPos, resultBlock.defaultBlockState(), 4);
            player.awardStat(FireflyStats.SYMBOL_STONES_CARVED.get());
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
