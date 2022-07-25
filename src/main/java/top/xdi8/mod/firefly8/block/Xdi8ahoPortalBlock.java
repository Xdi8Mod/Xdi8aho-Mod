package top.xdi8.mod.firefly8.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.ext.IPortalCooldownEntity;
import top.xdi8.mod.firefly8.world.Xdi8DimensionUtils;

/** @see net.minecraft.world.level.block.NetherPortalBlock */
public class Xdi8ahoPortalBlock extends Block {

    public Xdi8ahoPortalBlock() {
        super(Properties.of(Material.PORTAL, MaterialColor.GOLD)
                .lightLevel(s->11)
                .strength(-1)
                .noCollission()
                .sound(SoundType.AMETHYST)
        );
    }

    @SuppressWarnings("deprecation")
    public void entityInside(@NotNull BlockState pState, @NotNull Level pLevel,
                             @NotNull BlockPos pPos, @NotNull Entity pEntity) {
        if (pLevel.isClientSide()) return;
        IPortalCooldownEntity entityExt = IPortalCooldownEntity.xdi8$extend(pEntity);
        if (!pEntity.isPassenger() && !pEntity.isVehicle() &&
                pEntity.canChangeDimensions() && !entityExt.xdi8$isOnCooldown()) {
            ServerLevel level = (ServerLevel) pLevel;
            Xdi8DimensionUtils.teleportToXdi8aho(level, pEntity, pPos);
            entityExt.xdi8$resetShortCooldown();
        }
    }
}
