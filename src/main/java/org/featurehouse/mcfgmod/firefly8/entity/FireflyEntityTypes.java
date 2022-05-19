package org.featurehouse.mcfgmod.firefly8.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FireflyEntityTypes {
    public static final DeferredRegister<EntityType<?>> REGISTRY
            = DeferredRegister.create(ForgeRegistries.ENTITIES, "firefly8");
    public static final RegistryObject<EntityType<FireflyEntity>> FIREFLY
            = REGISTRY.register("firefly", () -> EntityType.Builder
            .of(FireflyEntity::new, MobCategory.AMBIENT).build("firefly8:firefly"));

}
