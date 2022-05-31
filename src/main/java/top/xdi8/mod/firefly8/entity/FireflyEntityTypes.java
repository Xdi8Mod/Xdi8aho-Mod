package top.xdi8.mod.firefly8.entity;

import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

public class FireflyEntityTypes {
    static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<EntityType<?>> REGISTRY
            = DeferredRegister.create(ForgeRegistries.ENTITIES, "firefly8");
    public static final RegistryObject<EntityType<FireflyEntity>> FIREFLY
            = REGISTRY.register("firefly", () -> EntityType.Builder
            .of(FireflyEntity::new, MobCategory.AMBIENT)
            .sized(0.5F, 0.5F)
            .clientTrackingRange(5).build("firefly8:firefly"));

}
