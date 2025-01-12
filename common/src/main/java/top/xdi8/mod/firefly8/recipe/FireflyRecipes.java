package top.xdi8.mod.firefly8.recipe;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import io.github.qwerty770.mcmod.xdi8.api.InternalRegistryLogWrapper;

import static io.github.qwerty770.mcmod.xdi8.util.registries.RegistryHelper.*;

public class FireflyRecipes {
    public static final InternalRegistryLogWrapper LOG_WRAPPER = InternalRegistryLogWrapper.firefly8("recipes");
    private static final String PRODUCE = "produce_symbol";
    private static final String TOTEM = "totem";

    // Recipe Book Category, for 1.21.2+
    public static final RegistrySupplier<RecipeBookCategory> PRODUCE_CATEGORY =
            recipeBookCategory(PRODUCE);
    public static final RegistrySupplier<RecipeBookCategory> TOTEM_CATEGORY =
            recipeBookCategory(TOTEM);
    // Recipe Serializer
    public static final RegistrySupplier<RecipeSerializer<SymbolStoneProductionRecipe>> PRODUCE_SERIALIZER =
            recipeSerializer(PRODUCE, SymbolStoneProductionRecipe.Serializer::new);
    public static final RegistrySupplier<RecipeSerializer<TotemRecipe>> TOTEM_SERIALIZER =
            recipeSerializer(TOTEM, TotemRecipe.Serializer::new);
    // Recipe Type
    public static final RegistrySupplier<RecipeType<SymbolStoneProductionRecipe>> PRODUCE_TYPE = recipeType(PRODUCE);
    public static final RegistrySupplier<RecipeType<TotemRecipe>> TOTEM_TYPE = recipeType(TOTEM);
}
