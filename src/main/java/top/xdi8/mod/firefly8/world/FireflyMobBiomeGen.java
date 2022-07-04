package top.xdi8.mod.firefly8.world;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class FireflyMobBiomeGen {
    public static void onBiomeLoading(BiomeLoadingEvent event) {
        final ResourceLocation id = event.getName();
        final var spawnSettings = event.getSpawns();
        // TODO: spawn rules
    }
}
