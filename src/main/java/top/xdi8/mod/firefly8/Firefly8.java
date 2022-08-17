package top.xdi8.mod.firefly8;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.featurehouse.mcmod.spm.platform.forge.ForgeBusWrapper;
import org.featurehouse.mcmod.spm.platform.forge.ForgeRegistryContainer;
import top.xdi8.mod.firefly8.advancement.criteria.FireflyCriteria;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import top.xdi8.mod.firefly8.block.entity.FireflyBlockEntityTypes;
import top.xdi8.mod.firefly8.core.letters.LettersUtil;
import top.xdi8.mod.firefly8.core.totem.TotemAbilities;
import top.xdi8.mod.firefly8.entity.FireflyEntity;
import top.xdi8.mod.firefly8.entity.FireflyEntityTypes;
import top.xdi8.mod.firefly8.item.FireflyItems;
import top.xdi8.mod.firefly8.item.tint.brewing.TintedPotionBrewing;
import top.xdi8.mod.firefly8.network.FireflyNetwork;
import top.xdi8.mod.firefly8.particle.FireflyParticles;
import top.xdi8.mod.firefly8.recipe.FireflyRecipes;
import top.xdi8.mod.firefly8.screen.FireflyMenus;
import top.xdi8.mod.firefly8.stats.FireflyStats;
import top.xdi8.mod.firefly8.world.Xdi8PoiTypes;

@Mod("firefly8")
public class Firefly8 {
    public Firefly8() {
        activateRegistries();
        ForgeRegistryContainer.of("firefly8").subscribeModBus(modBus());

        // Item
        TintedPotionBrewing.register();

        // Entity
        modBus().addListener(this::registerEntityAttributes);
        //forgeBus().addListener(FireflyMobBiomeGen::onBiomeLoading);

        // Common
        modBus().addListener(this::onCommonSetup);

        // Network
        FireflyNetwork.init();

        // Client: use bus subscriber
    }

    private static void activateRegistries() {
        // Block
        FireflyBlocks.LOG_WRAPPER.run();
        FireflyBlockEntityTypes.LOG_WRAPPER.run();
        Xdi8PoiTypes.LOG_WRAPPER.run();
        // Item
        FireflyItems.LOG_WRAPPER.run();
        // Entity
        FireflyEntityTypes.LOG_WRAPPER.run();
        // Recipe
        FireflyRecipes.LOG_WRAPPER.run();
        // Stats
        FireflyStats.LOG_WRAPPER.run();
        // Particle
        FireflyParticles.LOG_WRAPPER.run();
        // Menu
        FireflyMenus.LOG_WRAPPER.run();
    }

    private static IEventBus modBus() {
        return FMLJavaModLoadingContext.get().getModEventBus();
    }

    private void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(FireflyEntityTypes.FIREFLY.get(), FireflyEntity.createAttributes().build());
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // Letters
            LettersUtil.fireLetterRegistry(new ForgeBusWrapper(modBus()));
            // Totem
            TotemAbilities.fireRegistry(new ForgeBusWrapper(modBus()));
            // Criteria
            FireflyCriteria.init();
        });
    }
}
