package top.xdi8.mod.firefly8.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.featurehouse.mcmod.spm.platform.api.reg.PlatformRegister;
import top.xdi8.mod.firefly8.util.InternalRegistryLogWrapper;

import java.util.function.Supplier;

public class FireflyEntityTypes {
    public static final InternalRegistryLogWrapper LOG_WRAPPER = InternalRegistryLogWrapper.firefly8("entity_types");

    public static final Supplier<EntityType<FireflyEntity>> FIREFLY
            = PlatformRegister.of("firefly8").entityType("firefly", () -> EntityType.Builder
            .of(FireflyEntity::new, MobCategory.AMBIENT)
            .sized(0.5F, 0.5F)
            .clientTrackingRange(5));

}
