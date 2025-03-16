package top.xdi8.mod.firefly8.forge;

import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.minecraft.client.data.models.BlockModelGenerators;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.ModDataGen;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import top.xdi8.mod.firefly8.block.symbol.SymbolStoneBlock;
import top.xdi8.mod.firefly8.core.letters.LettersUtil;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = "firefly8", bus = EventBusSubscriber.Bus.MOD)
public class ModDataGenForge {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        event.createProvider(ModModelProvider::new);
        event.createProvider(ModRecipeProvider.Runner::new);
    }

    private static class ModModelProvider extends ModelProvider {
        public ModModelProvider(PackOutput output) {
            super(output, "firefly8");
        }

        @Override
        protected void registerModels(@NotNull BlockModelGenerators blockModels, @NotNull ItemModelGenerators itemModels) {
            blockModels.family(FireflyBlocks.CEDAR_PLANKS.get()).generateFor(ModDataGen.FAMILY);
            blockModels.createTintedLeaves(FireflyBlocks.CEDAR_LEAVES.get(), TexturedModel.LEAVES, FoliageColor.FOLIAGE_DEFAULT);
            blockModels.woodProvider(FireflyBlocks.CEDAR_LOG.get()).logWithHorizontal(FireflyBlocks.CEDAR_LOG.get()).wood(FireflyBlocks.CEDAR_WOOD.get());
            blockModels.woodProvider(FireflyBlocks.STRIPPED_CEDAR_LOG.get()).logWithHorizontal(FireflyBlocks.STRIPPED_CEDAR_LOG.get()).wood(FireflyBlocks.STRIPPED_CEDAR_WOOD.get());

            ModDataGen.getTrivialCubeBlocks().forEach(blockModels::createTrivialCube);
            LettersUtil.forEach((key, letter) -> blockModels.createTrivialCube(SymbolStoneBlock.fromLetter(letter)));
        }

        @Override
        public @NotNull String getName() {
            return "Firefly8 Models";
        }
    }

    private static class ModRecipeProvider extends RecipeProvider {
        private ModRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
            super(provider, output);
        }

        @Override
        public void buildRecipes() {
            BlockFamily blockFamily = ModDataGen.FAMILY;
            blockFamily.getVariants().forEach((variant, block) -> {
                RecipeProvider.FamilyRecipeProvider familyRecipeProvider = SHAPE_BUILDERS.get(variant);
                ItemLike itemLike = this.getBaseBlock(blockFamily, variant);
                if (familyRecipeProvider != null) {
                    RecipeBuilder recipeBuilder = familyRecipeProvider.create(this, block, itemLike);
                    blockFamily.getRecipeGroupPrefix().ifPresent(string -> recipeBuilder.group(string +  "_" + variant.getRecipeGroup()));
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
}
