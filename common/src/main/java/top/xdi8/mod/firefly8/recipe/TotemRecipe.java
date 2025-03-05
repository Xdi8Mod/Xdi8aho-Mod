package top.xdi8.mod.firefly8.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.qwerty770.mcmod.xdi8.api.ResourceLocationTool;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.xdi8.mod.firefly8.core.letters.KeyedLetter;
import top.xdi8.mod.firefly8.core.letters.LettersUtil;
import top.xdi8.mod.firefly8.core.totem.TotemAbilities;
import top.xdi8.mod.firefly8.core.totem.TotemAbility;
import top.xdi8.mod.firefly8.item.FireflyItemTags;
import top.xdi8.mod.firefly8.item.symbol.SymbolStoneBlockItem;
import top.xdi8.mod.firefly8.item.symbol.Xdi8TotemItem;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
public class TotemRecipe implements Recipe<TotemRecipeInput> {
    public Ingredient input;
    public final List<KeyedLetter> letters;
    public final TotemAbility ability;
    private @Nullable PlacementInfo placementInfo;

    public TotemRecipe(List<KeyedLetter> letters, TotemAbility ability){
        this.letters = letters;
        this.ability = ability;
    }

    public static TotemRecipe fromStrings(List<String> letters, String ability){
        return new TotemRecipe(letters.stream().map((letter) -> LettersUtil.byId(ResourceLocationTool.create(letter))).toList(),
                TotemAbilities.byId(ResourceLocationTool.create(ability)).orElseThrow(() -> new IllegalArgumentException("Invalid totem ability: " + ability)));
    }

    @Override
    public boolean matches(TotemRecipeInput input, Level level) {
        final int inputSize = input.size();
        final int lettersSize = letters.size();
        if (inputSize <= lettersSize)
            return false;
        final ItemStack totem = input.getItem(0);
        if (!totem.is(FireflyItemTags.TOTEM.tagKey()))
            return false;
        if (Xdi8TotemItem.getAbility(totem) != null)
            return false;

        for (int i = 1; i < inputSize; i++) {
            if (i > lettersSize) { // more slots than specified
                if (!input.getItem(i).isEmpty()) return false;
                else continue;
            }
            KeyedLetter letter = letters.get(i-1);
            if (!letter.isNull()) {
                SymbolStoneBlockItem matchingItem = SymbolStoneBlockItem.fromLetter(letter);
                if (!input.getItem(i).is(matchingItem)) return false;
            } else {
                if (!input.getItem(i).isEmpty()) return false;
            }
        }
        return true;
    }

    @Override
    public @NotNull ItemStack assemble(TotemRecipeInput input, HolderLookup.Provider registries) {
        return Xdi8TotemItem.withTotemAbility(input.getItem(0).copy(), ability);
    }

    @Override
    public @NotNull RecipeSerializer<? extends Recipe<TotemRecipeInput>> getSerializer() {
        return FireflyRecipes.TOTEM_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<TotemRecipe> getType() {
        return FireflyRecipes.TOTEM_TYPE.get();
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
        return FireflyRecipes.TOTEM_CATEGORY.get();
    }

    public static class Serializer implements RecipeSerializer<TotemRecipe> {
        private static final MapCodec<TotemRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
                instance.group(Codec.STRING.listOf().fieldOf("letters").forGetter((arg) ->
                                arg.letters.stream().map((letter -> letter.id().toString())).toList()),
                               Codec.STRING.fieldOf("ability").forGetter((arg) -> arg.ability.getId().toString()))
                        .apply(instance, TotemRecipe::fromStrings));
        private static final StreamCodec<RegistryFriendlyByteBuf, TotemRecipe> STREAM_CODEC =
                StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        public static @NotNull TotemRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
            final String ability = buf.readUtf();
            TotemAbility totemAbility = TotemAbilities.byId(ResourceLocationTool.create(ability))
                    .orElseThrow(() -> new IllegalArgumentException("Invalid totem ability: " + ability));

            final int lettersSize = buf.readInt();
            List<KeyedLetter> letterList = new ArrayList<>(lettersSize);
            for (int i = 0; i < lettersSize; i++) {
                String letter = buf.readUtf();
                final KeyedLetter keyedLetter = LettersUtil.byId(ResourceLocationTool.create(letter));
                letterList.add(keyedLetter);
            }
            return new TotemRecipe(letterList, totemAbility);
        }

        public static void toNetwork(RegistryFriendlyByteBuf buf, @NotNull TotemRecipe recipe) {
            buf.writeUtf(recipe.ability.getId().toString());
            List<String> letters = recipe.letters.stream()
                    .map(KeyedLetter::id)
                    .map(ResourceLocation::toString)
                    .toList();
            buf.writeInt(letters.size());
            for (String letter : letters) {
                buf.writeUtf(letter);
            }
        }

        @Override
        public @NotNull MapCodec<TotemRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, TotemRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
