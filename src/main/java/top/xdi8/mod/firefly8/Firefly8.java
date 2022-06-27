package top.xdi8.mod.firefly8;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.xdi8.mod.firefly8.entity.FireflyEntity;
import top.xdi8.mod.firefly8.entity.FireflyEntityTypes;
import top.xdi8.mod.firefly8.item.FireflyItems;
import top.xdi8.mod.firefly8.item.tint.brewing.TintedPotionBrewing;
import top.xdi8.mod.firefly8.particle.FireflyParticles;

@Mod("firefly8")
public class Firefly8 {
    public Firefly8() {
        FireflyItems.REGISTRY.register(modBus());
        TintedPotionBrewing.register();

        FireflyEntityTypes.REGISTRY.register(modBus());
        modBus().addListener(this::registerEntityAttributes);

        FireflyParticles.REGISTRY.register(modBus());

        modBus().addListener(this::onClientSetup);
        modBus().addListener(FireflyClientSetup::registerParticles);
    }

    private static IEventBus modBus() {
        return FMLJavaModLoadingContext.get().getModEventBus();
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(new FireflyClientSetup());
    }

    private void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(FireflyEntityTypes.FIREFLY.get(), FireflyEntity.createAttributes().build());
    }
}
