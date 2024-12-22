package top.xdi8.mod.firefly8.forge;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.brewing.BrewingRecipe;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.neoforgespi.Environment;
import top.xdi8.mod.firefly8.Firefly8;
import top.xdi8.mod.firefly8.Firefly8Client;
import top.xdi8.mod.firefly8.item.FireflyItemTags;
import top.xdi8.mod.firefly8.network.FireflyNetwork;

@Mod("firefly8")
public class Firefly8Forge {
    public Firefly8Forge() {
        Firefly8.init();
        if (Environment.get().getDist().equals(Dist.CLIENT)){
            FireflyNetwork.registerClientNetwork();
            Firefly8Client.registerParticles();
        }
        FireflyNetwork.registerServerNetwork();
    }

    @EventBusSubscriber(modid = "firefly8", bus = EventBusSubscriber.Bus.MOD)
    public static final class FireflyEvents {
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

        @SubscribeEvent
        public static void registerBrewingRecipes(RegisterBrewingRecipesEvent event){
            event.getBuilder().addRecipe(new BrewingRecipe(
                    Ingredient.of(Items.SPLASH_POTION),
                    Ingredient.of(FireflyItemTags.TINTED_DRAGON_BREATH.entries()),
                    Items.LINGERING_POTION.getDefaultInstance()));
        }
    }
}
