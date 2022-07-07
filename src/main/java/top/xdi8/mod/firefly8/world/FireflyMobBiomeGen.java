package top.xdi8.mod.firefly8.world;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.xdi8.mod.firefly8.entity.FireflyEntityTypes;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = "firefly8")
public class FireflyMobBiomeGen {
    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {
        final Biome.BiomeCategory biomeCategory = event.getCategory();
        final var spawnSettings = event.getSpawns();
        if (Objects.equals(biomeCategory, Biome.BiomeCategory.PLAINS)) {
            spawnSettings.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(
                    FireflyEntityTypes.FIREFLY.get(), 5, 1, 3));
        } else if (Objects.equals(biomeCategory, Biome.BiomeCategory.FOREST)) {
            spawnSettings.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(
                    FireflyEntityTypes.FIREFLY.get(), 10, 2, 4));
        } else if (Objects.equals(biomeCategory, Biome.BiomeCategory.JUNGLE)) {
            spawnSettings.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(
                    FireflyEntityTypes.FIREFLY.get(), 20, 2, 4));
        } else if (Objects.equals(biomeCategory, Biome.BiomeCategory.SWAMP)) {
            spawnSettings.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(
                    FireflyEntityTypes.FIREFLY.get(), 7, 2, 4));
        }
    }
}
