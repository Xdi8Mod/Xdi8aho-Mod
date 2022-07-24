package top.xdi8.mod.firefly8.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.world.Xdi8TeleporterImpl;

/** @see net.minecraft.world.level.block.NetherPortalBlock */
public class Xdi8ahoPortalBlock extends Block {

    public Xdi8ahoPortalBlock() {
        super(Properties.of(Material.PORTAL)
                .lightLevel(s->11)
                .instabreak()
                .noCollission()
                .sound(SoundType.AMETHYST)
        );
    }

    @SuppressWarnings("unused") // not used yet
    public void entityInside0(@NotNull BlockState pState, @NotNull Level pLevel,
                             @NotNull BlockPos pPos, @NotNull Entity pEntity) {
        if (pLevel.isClientSide()) return;
        if (!pEntity.isPassenger() && !pEntity.isVehicle() &&
                pEntity.canChangeDimensions()) {
            ServerLevel level = (ServerLevel) pLevel;
            pEntity.changeDimension((level),
                    new Xdi8TeleporterImpl(level));
        }
    }
}
