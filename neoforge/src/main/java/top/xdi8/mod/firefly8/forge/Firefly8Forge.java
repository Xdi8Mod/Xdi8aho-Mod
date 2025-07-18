package top.xdi8.mod.firefly8.forge;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.brewing.BrewingRecipe;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import top.xdi8.mod.firefly8.Firefly8;
import top.xdi8.mod.firefly8.item.FireflyItemTags;

@Mod("firefly8")
public class Firefly8Forge {
    public Firefly8Forge() {
        Firefly8.init();
    }

    @EventBusSubscriber(modid = "firefly8", bus = EventBusSubscriber.Bus.MOD)
    public static final class Firefly8ModEvents {
        @SubscribeEvent
        public static void onCommonSetup(FMLCommonSetupEvent event) {
            event.enqueueWork(Firefly8::commonSetup);
        }

        private static boolean registered = false;
        @SubscribeEvent
        public static void register(RegisterEvent event){
            if (!registered){
                Firefly8.activateRegistries();
                registered = true;
            }
        }
    }

    @EventBusSubscriber(modid = "firefly8", bus = EventBusSubscriber.Bus.GAME)
    public static final class Firefly8GameEvents {
        @SubscribeEvent
        public static void registerBrewingRecipes(RegisterBrewingRecipesEvent event){
            try {
                event.getBuilder().addRecipe(new BrewingRecipe(
                        Ingredient.of(Items.SPLASH_POTION),
                        Ingredient.of(FireflyItemTags.TINTED_DRAGON_BREATH.entries()),
                        Items.LINGERING_POTION.getDefaultInstance()));
            }
            catch (Exception e){
                Firefly8.LOGGER.warn(e.toString());
            }
        }
    }
}
