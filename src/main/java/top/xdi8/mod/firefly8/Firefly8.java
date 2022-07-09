package top.xdi8.mod.firefly8;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.xdi8.mod.firefly8.block.FIreflyBlocks;
import top.xdi8.mod.firefly8.entity.FireflyEntity;
import top.xdi8.mod.firefly8.entity.FireflyEntityTypes;
import top.xdi8.mod.firefly8.item.FireflyItems;
import top.xdi8.mod.firefly8.item.tint.brewing.TintedPotionBrewing;
import top.xdi8.mod.firefly8.particle.FireflyParticles;
import top.xdi8.mod.firefly8.world.FireflyMobBiomeGen;

@Mod("firefly8")
public class Firefly8 {
    public Firefly8() {
        // Block
        FIreflyBlocks.REGISTRY.register(modBus());
        // Item
        FireflyItems.REGISTRY.register(modBus());
        TintedPotionBrewing.register();

        // Entity
        FireflyEntityTypes.REGISTRY.register(modBus());
        modBus().addListener(this::registerEntityAttributes);
        forgeBus().addListener(FireflyMobBiomeGen::onBiomeLoading);

        // Particle
        FireflyParticles.REGISTRY.register(modBus());

        // Client: use bus subscriber
    }

    private static IEventBus modBus() {
        return FMLJavaModLoadingContext.get().getModEventBus();
    }

    private static IEventBus forgeBus() {
        return MinecraftForge.EVENT_BUS;
    }

    private void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(FireflyEntityTypes.FIREFLY.get(), FireflyEntity.createAttributes().build());
    }
}
