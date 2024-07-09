package top.xdi8.mod.firefly8.world.forge;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraftforge.common.util.ITeleporter;
import org.jetbrains.annotations.Nullable;
import top.xdi8.mod.firefly8.ext.IPortalCooldownEntity;
import top.xdi8.mod.firefly8.world.TeleportWrapper;

import java.util.function.Function;

public class Xdi8DimensionUtilsImpl {
    public static Entity changeDimension(Entity e, ServerLevel xdi8Level, TeleportWrapper teleporter) {
        return e.changeDimension(xdi8Level, new O2XTeleportWrapper(teleporter));
    }

    public record O2XTeleportWrapper(TeleportWrapper wrapped) implements ITeleporter {
        @Nullable
        @Override
        public PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld, Function defaultPortalInfo) {
            return wrapped().getPortalInfo(entity, destWorld);
        }

        public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
            final Entity entity1 = ITeleporter.super.placeEntity(entity, currentWorld, destWorld, yaw, repositionEntity);
            IPortalCooldownEntity.xdi8$extend(entity1).xdi8$resetCooldown();
            return entity1;
        }
    }
}
