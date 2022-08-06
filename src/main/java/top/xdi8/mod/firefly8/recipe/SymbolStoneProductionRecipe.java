package top.xdi8.mod.firefly8.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.featurehouse.mcmod.spm.platform.api.recipe.SimpleRecipeSerializer;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.core.letters.KeyedLetter;
import top.xdi8.mod.firefly8.core.letters.LettersUtil;
import top.xdi8.mod.firefly8.item.symbol.SymbolStoneBlockItem;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public record SymbolStoneProductionRecipe(ResourceLocation id, KeyedLetter letter, int weight) implements Recipe<Container> {

    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        return true;
    }

    @Override
    public @NotNull ItemStack assemble(Container pContainer) {
        return getResultItem();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem() {
        return new ItemStack(SymbolStoneBlockItem.fromLetter(letter));
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return FireflyRecipes.PRODUCE_S.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return FireflyRecipes.PRODUCE_T.get();
    }

    public static class Serializer extends SimpleRecipeSerializer<SymbolStoneProductionRecipe>{

        @Override
        public SymbolStoneProductionRecipe readJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            final String letter = GsonHelper.getAsString(pSerializedRecipe, "letter");
            final KeyedLetter keyedLetter = LettersUtil.byId(new ResourceLocation(letter));
            final int weight = GsonHelper.getAsInt(pSerializedRecipe, "weight");
            return new SymbolStoneProductionRecipe(pRecipeId, keyedLetter, weight);
        }

        @Override
        public SymbolStoneProductionRecipe readPacket(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            final String letter = pBuffer.readUtf();
            final KeyedLetter keyedLetter = LettersUtil.byId(new ResourceLocation(letter));
            final int weight = pBuffer.readInt();
            return new SymbolStoneProductionRecipe(pRecipeId, keyedLetter, weight);
        }

        @Override
        public void writePacket(FriendlyByteBuf pBuffer, SymbolStoneProductionRecipe pRecipe) {
            pBuffer.writeUtf(pRecipe.letter().id().toString());
            pBuffer.writeInt(pRecipe.weight());
        }
    }
}
