package top.xdi8.mod.firefly8.forge;

import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
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
import top.xdi8.mod.firefly8.item.FireflyItems;

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
            blockModels.createPlantWithDefaultItem(FireflyBlocks.CEDAR_SAPLING.get(), FireflyBlocks.POTTED_CEDAR_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED);
            ModDataGen.getTrivialCubeBlocks().forEach(blockModels::createTrivialCube);
            LettersUtil.forEach((key, letter) -> blockModels.createTrivialCube(SymbolStoneBlock.fromLetter(letter)));

            itemModels.generateFlatItem(FireflyItems.BUNDLER.get(), Items.BUNDLE, ModelTemplates.FLAT_ITEM);
            Item tinted_potion = FireflyItems.TINTED_POTION.get();
            itemModels.generateFlatItem(FireflyItems.TINTED_DRAGON_BREATH.get(), tinted_potion, ModelTemplates.FLAT_ITEM);
            itemModels.generateFlatItem(FireflyItems.TINTED_FIREFLY_BOTTLE.get(), tinted_potion, ModelTemplates.FLAT_ITEM);
            itemModels.generateFlatItem(FireflyItems.TINTED_GLASS_BOTTLE.get(), tinted_potion, ModelTemplates.FLAT_ITEM);
            itemModels.generateFlatItem(FireflyItems.TINTED_HONEY_BOTTLE.get(), tinted_potion, ModelTemplates.FLAT_ITEM);
            ModDataGen.getFlatItems().forEach(item -> itemModels.generateFlatItem(item, ModelTemplates.FLAT_ITEM));
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
