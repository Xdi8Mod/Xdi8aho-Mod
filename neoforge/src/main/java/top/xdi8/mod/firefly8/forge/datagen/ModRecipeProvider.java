package top.xdi8.mod.firefly8.forge.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.ModDataGen;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import top.xdi8.mod.firefly8.item.FireflyItems;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    private ModRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
        super(provider, output);
    }

    @Override
    public void buildRecipes(){
        buildRecipes(ModDataGen.REDWOOD_FAMILY);
        buildRecipes(ModDataGen.SYMBOL_STONE_FAMILY);
        this.woodenBoat(FireflyItems.CEDAR_BOAT.get(), FireflyBlocks.CEDAR_PLANKS.get());
        this.chestBoat(FireflyItems.CEDAR_CHEST_BOAT.get(), FireflyBlocks.CEDAR_PLANKS.get());
        this.hangingSign(FireflyItems.CEDAR_HANGING_SIGN.get(), FireflyBlocks.STRIPPED_CEDAR_LOG.get());
        this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, FireflyBlocks.SYMBOL_STONE_BRICK_SLAB.get(), FireflyBlocks.SYMBOL_STONE_BRICKS.get(), 2);
        this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, FireflyBlocks.SYMBOL_STONE_BRICK_STAIRS.get(), FireflyBlocks.SYMBOL_STONE_BRICKS.get());
    }

    public void buildRecipes(BlockFamily blockFamily) {
        blockFamily.getVariants().forEach((variant, block) -> {
            RecipeProvider.FamilyRecipeProvider familyRecipeProvider = SHAPE_BUILDERS.get(variant);
            ItemLike itemLike = this.getBaseBlock(blockFamily, variant);
            if (familyRecipeProvider != null) {
                RecipeBuilder recipeBuilder = familyRecipeProvider.create(this, block, itemLike);
                blockFamily.getRecipeGroupPrefix().ifPresent(string -> recipeBuilder.group(string + "_" + variant.getRecipeGroup()));
                recipeBuilder.unlockedBy(blockFamily.getRecipeUnlockedBy().orElseGet(() -> getHasName(itemLike)), this.has(itemLike));
                recipeBuilder.save(this.output);
            }
        });
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected @NotNull RecipeProvider createRecipeProvider(@NotNull HolderLookup.Provider provider, @NotNull RecipeOutput output) {
            return new ModRecipeProvider(provider, output);
        }

        @Override
        public @NotNull String getName() {
            return "Firefly8 Recipes";
        }
    }
}
