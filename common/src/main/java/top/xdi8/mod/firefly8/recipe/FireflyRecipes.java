package top.xdi8.mod.firefly8.recipe;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import io.github.qwerty770.mcmod.xdi8.api.InternalRegistryLogWrapper;

import static io.github.qwerty770.mcmod.xdi8.util.registries.RegistryHelper.recipeSerializer;
import static io.github.qwerty770.mcmod.xdi8.util.registries.RegistryHelper.recipeType;

public class FireflyRecipes {
    public static final InternalRegistryLogWrapper LOG_WRAPPER = InternalRegistryLogWrapper.firefly8("recipes");
    public static final RegistrySupplier<RecipeType<SymbolStoneProductionRecipe>> PRODUCE_T = recipeType("produce_symbol");
    public static final RegistrySupplier<RecipeSerializer<SymbolStoneProductionRecipe>> PRODUCE_S = recipeSerializer("produce_symbol",
            SymbolStoneProductionRecipe.Serializer::new);

    public static final RegistrySupplier<RecipeType<TotemRecipe>> TOTEM_T = recipeType("totem");
    public static final RegistrySupplier<RecipeSerializer<TotemRecipe>> TOTEM_S = recipeSerializer("totem",
            TotemRecipe.Serializer::new);

}
