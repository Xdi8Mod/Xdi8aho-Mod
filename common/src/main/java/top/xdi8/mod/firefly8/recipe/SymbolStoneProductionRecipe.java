package top.xdi8.mod.firefly8.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.qwerty770.mcmod.xdi8.api.ResourceLocationTool;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.xdi8.mod.firefly8.Firefly8;
import top.xdi8.mod.firefly8.core.letters.KeyedLetter;
import top.xdi8.mod.firefly8.core.letters.LettersUtil;
import top.xdi8.mod.firefly8.item.FireflyItems;
import top.xdi8.mod.firefly8.item.symbol.SymbolStoneBlockItem;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.NoSuchElementException;

@ParametersAreNonnullByDefault
public class SymbolStoneProductionRecipe implements Recipe<SingleRecipeInput> {
    public Ingredient input;
    public KeyedLetter letter;
    public int weight;
    private @Nullable PlacementInfo placementInfo;

    public SymbolStoneProductionRecipe(KeyedLetter letter, int weight) {
        this.input = Ingredient.of(SymbolStoneBlockItem.fromLetter(KeyedLetter.empty()));
        this.letter = letter;
        this.weight = weight;
    }

    public SymbolStoneProductionRecipe(String string, int weight) {
        this(LettersUtil.byId(ResourceLocationTool.create(string)), weight);
    }

    @Override
    public boolean matches(SingleRecipeInput singleRecipeInput, Level level) {
        return this.input.test(singleRecipeInput.item());
    }

    @Override
    public @NotNull ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider registries) {
        ResourceLocation letterId = letter.id();
        ResourceLocation itemId;
        if (Firefly8.MODID.equals(letter.id().getNamespace()))
            itemId = ResourceLocationTool.create(Firefly8.MODID, "symbol_stone_" + letterId.getPath());
        else
            itemId = ResourceLocationTool.create(letterId.getNamespace(), "symbol_stone_" + letterId.getNamespace() + "__" + letterId.getPath());
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
                instance.group(Codec.STRING.fieldOf("letter").forGetter((arg) -> arg.letter.id().toString()),
                                Codec.INT.fieldOf("weight").forGetter((arg) -> arg.weight))
                        .apply(instance, SymbolStoneProductionRecipe::new));
        private static final StreamCodec<RegistryFriendlyByteBuf, SymbolStoneProductionRecipe> STREAM_CODEC =
                StreamCodec.of(SymbolStoneProductionRecipe.Serializer::toNetwork, SymbolStoneProductionRecipe.Serializer::fromNetwork);

        public static SymbolStoneProductionRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            final String letter = buffer.readUtf();
            final KeyedLetter keyedLetter = LettersUtil.byId(ResourceLocationTool.create(letter));
            final int weight = buffer.readInt();
            return new SymbolStoneProductionRecipe(keyedLetter, weight);
        }

        public static void toNetwork(RegistryFriendlyByteBuf buffer, @NotNull SymbolStoneProductionRecipe recipe) {
            buffer.writeUtf(recipe.letter.id().toString());
            buffer.writeInt(recipe.weight);
        }

        @Override
        public @NotNull MapCodec<SymbolStoneProductionRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, SymbolStoneProductionRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
