package top.xdi8.mod.firefly8.recipe;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.featurehouse.mcmod.spm.platform.api.reg.PlatformRegister;
import io.github.qwerty770.mcmod.spmreborn.api.InternalRegistryLogWrapper;

public class FireflyRecipes {
    private static final PlatformRegister reg = PlatformRegister.of("firefly8");
    public static final InternalRegistryLogWrapper LOG_WRAPPER = InternalRegistryLogWrapper.firefly8("recipes");
    public static final RegistrySupplier<RecipeType<SymbolStoneProductionRecipe>> PRODUCE_T = reg.recipeType("produce_symbol");
    public static final RegistrySupplier<RecipeSerializer<SymbolStoneProductionRecipe>> PRODUCE_S = reg.recipeSerializer("produce_symbol",
            SymbolStoneProductionRecipe.Serializer::new);

    public static final RegistrySupplier<RecipeType<TotemRecipe>> TOTEM_T = reg.recipeType("totem");
    public static final RegistrySupplier<RecipeSerializer<TotemRecipe>> TOTEM_S = reg.recipeSerializer("totem",
            TotemRecipe.Serializer::new);

}
