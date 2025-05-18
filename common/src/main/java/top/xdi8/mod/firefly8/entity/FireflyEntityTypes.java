package top.xdi8.mod.firefly8.entity;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import io.github.qwerty770.mcmod.xdi8.registries.InternalRegistryLogWrapper;

import static io.github.qwerty770.mcmod.xdi8.registries.RegistryHelper.entityType;

public class FireflyEntityTypes {
    public static final InternalRegistryLogWrapper LOG_WRAPPER = InternalRegistryLogWrapper.firefly8("entity_types");
    public static final RegistrySupplier<EntityType<FireflyEntity>> FIREFLY =
            entityType("firefly", () -> EntityType.Builder
            .of(FireflyEntity::new, MobCategory.CREATURE)
            .sized(0.5F, 0.5F)
            .clientTrackingRange(5));
}
