package top.xdi8.mod.firefly8.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.ModDataGen;
import top.xdi8.mod.firefly8.block.FireflyBlocks;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
        return new ModRecipeProviderInner(provider, recipeOutput);
    }

    @Override
    public @NotNull String getName() {
        return "Firefly8 Recipes";
    }

    private static class ModRecipeProviderInner extends RecipeProvider {
        private ModRecipeProviderInner(HolderLookup.Provider provider, RecipeOutput output) {
            super(provider, output);
        }

        @Override
        public void buildRecipes() {
            buildRecipes(ModDataGen.REDWOOD_FAMILY);
            buildRecipes(ModDataGen.SYMBOL_STONE_FAMILY);
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
    }
}
