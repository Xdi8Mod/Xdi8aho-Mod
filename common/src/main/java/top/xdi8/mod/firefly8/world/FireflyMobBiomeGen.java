package top.xdi8.mod.firefly8.world;

import dev.architectury.registry.level.biome.BiomeModifications;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import top.xdi8.mod.firefly8.entity.FireflyEntityTypes;

public class FireflyMobBiomeGen {
    public static void registerBiomeModifications() {
        BiomeModifications.addProperties(((biomeContext, mutable) -> {
            if (biomeContext.hasTag(BiomeTags.HAS_VILLAGE_PLAINS)){
                mutable.getSpawnProperties().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(
                        FireflyEntityTypes.FIREFLY.get(), 5, 1, 3));
            }
            else if (biomeContext.hasTag(BiomeTags.IS_FOREST)){
                mutable.getSpawnProperties().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(
                        FireflyEntityTypes.FIREFLY.get(), 10, 2, 4));
            }
            else if (biomeContext.hasTag(BiomeTags.IS_JUNGLE)){
                mutable.getSpawnProperties().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(
                        FireflyEntityTypes.FIREFLY.get(), 20, 2, 4));
            }
            else if (biomeContext.hasTag(BiomeTags.HAS_SWAMP_HUT)){
                mutable.getSpawnProperties().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(
                        FireflyEntityTypes.FIREFLY.get(), 7, 2, 4));
            }
        }));
    }
}
