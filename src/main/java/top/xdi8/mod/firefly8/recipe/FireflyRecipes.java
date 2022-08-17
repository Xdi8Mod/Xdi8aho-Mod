package top.xdi8.mod.firefly8.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.featurehouse.mcmod.spm.platform.api.reg.PlatformRegister;
import top.xdi8.mod.firefly8.util.InternalRegistryLogWrapper;

import java.util.function.Supplier;

public class FireflyRecipes {
    private static final PlatformRegister reg = PlatformRegister.of("firefly8");
    public static final InternalRegistryLogWrapper LOG_WRAPPER = InternalRegistryLogWrapper.firefly8("recipes");
    public static final Supplier<RecipeType<SymbolStoneProductionRecipe>> PRODUCE_T = reg.recipeType("produce_symbol");
    public static final Supplier<RecipeSerializer<SymbolStoneProductionRecipe>> PRODUCE_S = reg.recipeSerializer("produce_symbol",
            SymbolStoneProductionRecipe.Serializer::new);

    public static final Supplier<RecipeType<TotemRecipe>> TOTEM_T = reg.recipeType("totem");
    public static final Supplier<RecipeSerializer<TotemRecipe>> TOTEM_S = reg.recipeSerializer("totem",
            TotemRecipe.Serializer::new);

}
