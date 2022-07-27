package top.xdi8.mod.firefly8.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.level.Level;
import org.featurehouse.mcmod.spm.platform.api.recipe.SimpleRecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class SymbolStoneProductionRecipe extends SingleItemRecipe {
    public SymbolStoneProductionRecipe(ResourceLocation pId, String pGroup, Ingredient pIngredient, ItemStack pResult) {
        super(FireflyRecipes.PRODUCE_T.get(), FireflyRecipes.PRODUCE_S.get(),
                pId, pGroup, pIngredient, pResult);
    }
    @Override
    public boolean matches(@NotNull Container inv, @NotNull Level pLevel) {
        return this.ingredient.test(inv.getItem(0));
    }

    static final class Serializer extends SimpleRecipeSerializer<SymbolStoneProductionRecipe> {
        public @NotNull SymbolStoneProductionRecipe readJson(@NotNull ResourceLocation pRecipeId, @NotNull JsonObject pJson) {
            String s = GsonHelper.getAsString(pJson, "group", "");
            Ingredient ingredient;
            if (GsonHelper.isArrayNode(pJson, "ingredient")) {
                ingredient = Ingredient.fromJson(GsonHelper.getAsJsonArray(pJson, "ingredient"));
            } else {
                ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(pJson, "ingredient"));
            }

            ItemStack itemStack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pJson, "result"));
            return new SymbolStoneProductionRecipe(pRecipeId, s, ingredient, itemStack);
        }

        public @NotNull SymbolStoneProductionRecipe readPacket(@NotNull ResourceLocation pRecipeId, @NotNull FriendlyByteBuf pBuffer) {
            String s = pBuffer.readUtf();
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            ItemStack itemstack = pBuffer.readItem();
            return new SymbolStoneProductionRecipe(pRecipeId, s, ingredient, itemstack);
        }

        public void writePacket(@NotNull FriendlyByteBuf pBuffer, @NotNull SymbolStoneProductionRecipe pRecipe) {
            pBuffer.writeUtf(pRecipe.group);
            pRecipe.ingredient.toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.result);
        }
    }
}
