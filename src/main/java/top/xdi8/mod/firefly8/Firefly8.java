package top.xdi8.mod.firefly8;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import top.xdi8.mod.firefly8.block.entity.FireflyBlockEntityTypes;
import top.xdi8.mod.firefly8.core.letters.LettersUtil;
import top.xdi8.mod.firefly8.entity.FireflyEntity;
import top.xdi8.mod.firefly8.entity.FireflyEntityTypes;
import top.xdi8.mod.firefly8.item.FireflyItems;
import top.xdi8.mod.firefly8.item.tint.brewing.TintedPotionBrewing;
import top.xdi8.mod.firefly8.network.FireflyNetwork;
import top.xdi8.mod.firefly8.particle.FireflyParticles;
import top.xdi8.mod.firefly8.screen.FireflyMenus;
import top.xdi8.mod.firefly8.world.Xdi8PoiTypes;

@Mod("firefly8")
public class Firefly8 {
    public Firefly8() {
        // Block
        FireflyBlocks.REGISTRY.register(modBus());
        FireflyBlockEntityTypes.REGISTRY.register(modBus());
        Xdi8PoiTypes.REGISTRY.register(modBus());
        // Item
        FireflyItems.REGISTRY.register(modBus());
        TintedPotionBrewing.register();

        // Entity
        FireflyEntityTypes.REGISTRY.register(modBus());
        modBus().addListener(this::registerEntityAttributes);
        //forgeBus().addListener(FireflyMobBiomeGen::onBiomeLoading);

        // Common
        modBus().addListener(this::onCommonSetup);

        // Particle
        FireflyParticles.REGISTRY.register(modBus());

        // Network
        FireflyNetwork.init();

        // Menu
        FireflyMenus.REGISTRY.register(modBus());
        // Client: use bus subscriber
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
            LettersUtil.fireLetterRegistry(modBus());
        });
    }
}
