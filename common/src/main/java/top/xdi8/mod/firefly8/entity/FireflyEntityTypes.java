package top.xdi8.mod.firefly8.entity;

import dev.architectury.registry.registries.RegistrySupplier;
import io.github.qwerty770.mcmod.xdi8.registries.InternalRegistryLogWrapper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import top.xdi8.mod.firefly8.item.FireflyItems;

import java.util.function.Supplier;

import static io.github.qwerty770.mcmod.xdi8.registries.RegistryHelper.entityType;

public class FireflyEntityTypes {
    public static final InternalRegistryLogWrapper LOG_WRAPPER = InternalRegistryLogWrapper.firefly8("entity_types");
    public static final RegistrySupplier<EntityType<Boat>> CEDAR_BOAT =
            entityType("cedar_boat", () ->
                    EntityType.Builder.of(boatFactory(FireflyItems.CEDAR_BOAT), MobCategory.MISC)
                            .noLootTable()
                            .sized(1.375F, 0.5625F)
                            .eyeHeight(0.5625F)
                            .clientTrackingRange(10));
    public static final RegistrySupplier<EntityType<ChestBoat>> CEDAR_CHEST_BOAT =
            entityType("cedar_chest_boat", () ->
                    EntityType.Builder.of(chestBoatFactory(FireflyItems.CEDAR_CHEST_BOAT), MobCategory.MISC)
                            .noLootTable()
                            .sized(1.375F, 0.5625F)
                            .eyeHeight(0.5625F)
                            .clientTrackingRange(10));
    public static final RegistrySupplier<EntityType<FireflyEntity>> FIREFLY =
            entityType("firefly", () -> EntityType.Builder
                    .of(FireflyEntity::new, MobCategory.CREATURE)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(5));

    private static EntityType.EntityFactory<Boat> boatFactory(Supplier<Item> boatItemGetter) {
        return (entityType, level) -> new Boat(entityType, level, boatItemGetter);
    }

    private static EntityType.EntityFactory<ChestBoat> chestBoatFactory(Supplier<Item> boatItemGetter) {
        return (entityType, level) -> new ChestBoat(entityType, level, boatItemGetter);
    }
}
