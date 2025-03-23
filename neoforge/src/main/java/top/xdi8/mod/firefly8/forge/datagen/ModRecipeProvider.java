package top.xdi8.mod.firefly8.forge.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.ModDataGen;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    private ModRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
        super(provider, output);
    }

    @Override
    public void buildRecipes(){
        buildRecipes(ModDataGen.REDWOOD_FAMILY);
        buildRecipes(ModDataGen.SYMBOL_STONE_FAMILY);
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
