package top.xdi8.mod.firefly8.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.qwerty770.mcmod.xdi8.api.ResourceLocationTool;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.xdi8.mod.firefly8.Firefly8;
import top.xdi8.mod.firefly8.core.letters.KeyedLetter;
import top.xdi8.mod.firefly8.core.letters.LettersUtil;
import top.xdi8.mod.firefly8.item.FireflyItems;
import top.xdi8.mod.firefly8.item.symbol.SymbolStoneBlockItem;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.NoSuchElementException;

@ParametersAreNonnullByDefault
public class SymbolStoneProductionRecipe implements Recipe<SingleRecipeInput> {
    public final Ingredient input;
    public final KeyedLetter letter;
    public final List<WeightEntry> weight;
    private @Nullable PlacementInfo placementInfo;

    public SymbolStoneProductionRecipe(KeyedLetter letter, List<WeightEntry> weight) {
        this.input = Ingredient.of(SymbolStoneBlockItem.fromLetter(KeyedLetter.empty()));
        this.letter = letter;
        this.weight = weight;
    }

    public SymbolStoneProductionRecipe(ResourceLocation location, List<WeightEntry> weight) {
        this(LettersUtil.byId(location), weight);
    }

    @Override
    public boolean matches(SingleRecipeInput singleRecipeInput, Level level) {
        return this.input.test(singleRecipeInput.item());
    }

    @Override
    public @NotNull ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider registries) {
        ResourceLocation letterId = letter.id();
        ResourceLocation itemId = ResourceLocationTool.create(letterId.getNamespace(), "symbol_stone_" + letterId.getPath());
        try {
            // Use HolderLookup here in 1.21+
            return new ItemStack(registries.lookup(Registries.ITEM).map((registry) -> registry.get(ResourceKey.create(Registries.ITEM, itemId)).orElseThrow()).orElseThrow());
        } catch (NoSuchElementException exception) {
            // Return the dark symbol stone as default, that's interesting
            Firefly8.LOGGER.error(exception.toString());
            return new ItemStack(FireflyItems.DARK_SYMBOL_STONE.get());
        }
    }

    @Override
    public @NotNull RecipeSerializer<? extends Recipe<SingleRecipeInput>> getSerializer() {
        return FireflyRecipes.PRODUCE_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<? extends Recipe<SingleRecipeInput>> getType() {
        return FireflyRecipes.PRODUCE_TYPE.get();
    }

    @Override
    public @NotNull PlacementInfo placementInfo() {
        if (this.placementInfo == null) {
            this.placementInfo = PlacementInfo.create(this.input);
        }
        return this.placementInfo;
    }

    @Override
    public @NotNull RecipeBookCategory recipeBookCategory() {
        return FireflyRecipes.PRODUCE_CATEGORY.get();
    }

    public static class Serializer implements RecipeSerializer<SymbolStoneProductionRecipe> {
        private static final MapCodec<SymbolStoneProductionRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
                instance.group(ResourceLocation.CODEC.fieldOf("letter").forGetter((recipe) -> recipe.letter.id()),
                                WeightEntry.CODEC.listOf().fieldOf("weight").forGetter((recipe) -> recipe.weight))
                        .apply(instance, SymbolStoneProductionRecipe::new));
        private static final StreamCodec<RegistryFriendlyByteBuf, SymbolStoneProductionRecipe> STREAM_CODEC =
                StreamCodec.composite(ResourceLocation.STREAM_CODEC, (recipe) -> recipe.letter.id(),
                        WeightEntry.STREAM_CODEC.apply(ByteBufCodecs.list()), (recipe) -> recipe.weight,
                        SymbolStoneProductionRecipe::new);

        @Override
        public @NotNull MapCodec<SymbolStoneProductionRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, SymbolStoneProductionRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }

    public record WeightEntry(TagKey<Block> tag, double weight) {
        public static final Codec<WeightEntry> CODEC = RecordCodecBuilder.create((instance) ->
                instance.group(TagKey.hashedCodec(Registries.BLOCK).fieldOf("block").forGetter((entry) -> entry.tag),
                                Codec.DOUBLE.optionalFieldOf("weight", 2.25).forGetter((entry) -> entry.weight))
                        .apply(instance, WeightEntry::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, WeightEntry> STREAM_CODEC =
                StreamCodec.composite(TagKey.streamCodec(Registries.BLOCK), WeightEntry::tag,
                        ByteBufCodecs.DOUBLE, WeightEntry::weight,
                        WeightEntry::new);
    }
}
