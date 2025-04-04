package top.xdi8.mod.firefly8.forge.datagen;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.ModDataGen;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import top.xdi8.mod.firefly8.block.symbol.SymbolStoneBlock;
import top.xdi8.mod.firefly8.core.letters.DefaultXdi8Letters;
import top.xdi8.mod.firefly8.core.letters.LettersUtil;
import top.xdi8.mod.firefly8.item.FireflyItems;

import static net.minecraft.client.data.models.model.TextureMapping.cube;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, "firefly8");
    }

    @Override
    protected void registerModels(@NotNull BlockModelGenerators blockModels, @NotNull ItemModelGenerators itemModels) {
        blockModels.family(FireflyBlocks.CEDAR_PLANKS.get()).generateFor(ModDataGen.REDWOOD_FAMILY);
        blockModels.family(FireflyBlocks.SYMBOL_STONE_BRICKS.get()).generateFor(ModDataGen.SYMBOL_STONE_FAMILY);
        blockModels.createTintedLeaves(FireflyBlocks.CEDAR_LEAVES.get(), TexturedModel.LEAVES, FoliageColor.FOLIAGE_DEFAULT);
        blockModels.woodProvider(FireflyBlocks.CEDAR_LOG.get()).logWithHorizontal(FireflyBlocks.CEDAR_LOG.get()).wood(FireflyBlocks.CEDAR_WOOD.get());
        blockModels.woodProvider(FireflyBlocks.STRIPPED_CEDAR_LOG.get()).logWithHorizontal(FireflyBlocks.STRIPPED_CEDAR_LOG.get()).wood(FireflyBlocks.STRIPPED_CEDAR_WOOD.get());
        blockModels.createPlantWithDefaultItem(FireflyBlocks.CEDAR_SAPLING.get(), FireflyBlocks.POTTED_CEDAR_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        ModDataGen.getTrivialCubeBlocks().forEach(blockModels::createTrivialCube);
        LettersUtil.forEach((key, letter) -> blockModels.createTrivialBlock(
                SymbolStoneBlock.fromLetter(letter), TexturedModel.createDefault(ModModelProvider::getSymbolStoneTexture, ModelTemplates.CUBE_ALL)));

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

    public static TextureMapping getSymbolStoneTexture(Block block1) {
        if (!(block1 instanceof SymbolStoneBlock block)) return cube(block1);
        ResourceLocation resourceLocation = BuiltInRegistries.BLOCK.getKey(block);
        if (block.letter() == DefaultXdi8Letters.LETTER_n){
            return cube(resourceLocation.withPath("block/symbol_stones/symbol_stone_n_img"));
        }
        if (block.letter() == DefaultXdi8Letters.LETTER_s){
            return cube(resourceLocation.withPath("block/symbol_stones/symbol_stone_s_img"));
        }
        return cube(resourceLocation.withPrefix("block/symbol_stones/"));
    }
}
