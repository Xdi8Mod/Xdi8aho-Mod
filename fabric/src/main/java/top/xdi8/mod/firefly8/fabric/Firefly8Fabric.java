package top.xdi8.mod.firefly8.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import top.xdi8.mod.firefly8.Firefly8;
import top.xdi8.mod.firefly8.item.FireflyItemTags;

public class Firefly8Fabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Firefly8.activateRegistries();
        Firefly8.init();
        Firefly8.commonSetup();
        registerBrewingRecipes();
        /*if (FabricLoader.getInstance().getEnvironmentType().equals(EnvType.CLIENT)) {
            FireflyNetwork.registerClientNetwork();
        }
        FireflyNetwork.registerServerNetwork();*/
    }

    private static void registerBrewingRecipes() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
                    try {
                        builder.registerItemRecipe(
                                Items.SPLASH_POTION,
                                Ingredient.of(FireflyItemTags.TINTED_DRAGON_BREATH.entries()),
                                Items.LINGERING_POTION);
                    } catch (Exception e) {
                        Firefly8.LOGGER.warn(e.toString());
                    }
                }
        );
    }
}
