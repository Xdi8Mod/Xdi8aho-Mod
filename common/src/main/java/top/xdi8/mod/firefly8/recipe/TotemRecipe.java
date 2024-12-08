package top.xdi8.mod.firefly8.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.architectury.core.AbstractRecipeSerializer;
import io.github.qwerty770.mcmod.xdi8.api.ResourceLocationTool;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.core.letters.KeyedLetter;
import top.xdi8.mod.firefly8.core.letters.LettersUtil;
import top.xdi8.mod.firefly8.core.totem.TotemAbilities;
import top.xdi8.mod.firefly8.core.totem.TotemAbility;
import top.xdi8.mod.firefly8.item.FireflyItemTags;
import top.xdi8.mod.firefly8.item.FireflyItems;
import top.xdi8.mod.firefly8.item.symbol.SymbolStoneBlockItem;
import top.xdi8.mod.firefly8.item.symbol.Xdi8TotemItem;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
public record TotemRecipe(ResourceLocation id,
                          List<KeyedLetter> letters, TotemAbility ability) implements Recipe<Container> {
    /**
     * Used to check if a recipe matches current crafting inventory
     */
    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        final int containerSize = pContainer.getContainerSize();
        final int lettersSize = letters().size();
        if (containerSize <= lettersSize)
            return false;
        final ItemStack totem = pContainer.getItem(0);
        if (!totem.is(FireflyItemTags.TOTEM.tagKey()))
            return false;
        if (Xdi8TotemItem.getAbility(totem) != null)
            return false;

        for (int i = 1; i < containerSize; i++) {
            if (i > lettersSize) { // more slots than specified
                if (!pContainer.getItem(i).isEmpty()) return false;
                else continue;
            }
            KeyedLetter letter = letters().get(i-1);
            if (!letter.isNull()) {
                SymbolStoneBlockItem matchingItem = SymbolStoneBlockItem.fromLetter(letter);
                if (!pContainer.getItem(i).is(matchingItem))
                    return false;
            } else {
                if (!pContainer.getItem(i).isEmpty())
                    return false;
            }
        }
        return true;
    }

    private ItemStack assemble(ItemStack item) {
        return Xdi8TotemItem.withTotemAbility(item, ability());
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    @Override
    public @NotNull ItemStack assemble(Container pContainer) {
        return assemble(pContainer.getItem(0).copy());
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    /**
     * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
     * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
     */
    @Override
    public @NotNull ItemStack getResultItem() {
        return assemble(FireflyItems.XDI8AHO_ICON.get().getDefaultInstance());
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return FireflyRecipes.TOTEM_S.get();
    }

    @Override
    public @NotNull RecipeType<TotemRecipe> getType() {
        return FireflyRecipes.TOTEM_T.get();
    }

    public static class Serializer extends AbstractRecipeSerializer<TotemRecipe> {
        @Override
        public @NotNull TotemRecipe fromJson(ResourceLocation pRecipeId, JsonObject obj) {
            final JsonArray letters = GsonHelper.getAsJsonArray(obj, "letters");
            final String ability = GsonHelper.getAsString(obj, "ability");

            List<KeyedLetter> letterList = new ArrayList<>();
            for (JsonElement e : letters) {
                String letter = GsonHelper.convertToString(e, "letter");
                final KeyedLetter keyedLetter = LettersUtil.byId(ResourceLocationTool.create(letter));
                letterList.add(keyedLetter);
            }
            TotemAbility totemAbility = TotemAbilities.byId(ResourceLocationTool.create(ability))
                    .orElseThrow(() -> new IllegalArgumentException("Invalid totem ability: " + ability));
            return new TotemRecipe(pRecipeId, letterList, totemAbility);
        }

        @Override
        public @NotNull TotemRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf buf) {
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
            return new TotemRecipe(pRecipeId, letterList, totemAbility);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, TotemRecipe recipe) {
            buf.writeUtf(recipe.ability().getId().toString());
            List<String> letters = recipe.letters().stream()
                    .map(KeyedLetter::id)
                    .map(ResourceLocation::toString)
                    .toList();
            buf.writeInt(letters.size());
            for (String letter : letters) {
                buf.writeUtf(letter);
            }
        }
    }
}
