package top.xdi8.mod.firefly8.recipe;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FireflyRecipes {
    public static final DeferredRegister<RecipeType<?>> TYPES = DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, "firefly8");
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, "firefly8");

    public static final RegistryObject<RecipeType<SymbolStoneProductionRecipe>> PRODUCE_T = ofType("produce_symbol");
    public static final RegistryObject<RecipeSerializer<SymbolStoneProductionRecipe>> PRODUCE_S = SERIALIZERS.register("produce_symbol",
            SymbolStoneProductionRecipe.Serializer::new);

    public static final RegistryObject<RecipeType<TotemRecipe>> TOTEM_T = ofType("totem");
    public static final RegistryObject<RecipeSerializer<TotemRecipe>> TOTEM_S = SERIALIZERS.register("totem",
            TotemRecipe.Serializer::new);

    private static <T extends Recipe<?>> RegistryObject<RecipeType<T>> ofType(String id) {
        return TYPES.register(id, () -> new RecipeType<>() {
            @Override
            public String toString() {
                return new ResourceLocation("firefly8", id).toString();
            }
        });
    }
}
