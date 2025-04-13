package top.xdi8.mod.firefly8.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.xdi8.mod.firefly8.block.BackPortalCoreBlock;
import top.xdi8.mod.firefly8.block.FireflyBlockTags;
import top.xdi8.mod.firefly8.block.FireflyBlocks;

@Mixin(BaseFireBlock.class)
public class BaseFireBlockMixin {
    @Inject(at = @At("HEAD"), cancellable = true,
            method = "getState(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;")
    private static void getState(BlockGetter level, BlockPos pPos, CallbackInfoReturnable<BlockState> cir) {
        BlockState state = level.getBlockState(pPos.below());
        if (state.is(FireflyBlockTags.BACK_PORTAL_FIRE_PLACEABLE.tagKey())) {
            if (!state.getOptionalValue(BackPortalCoreBlock.IS_VALID).orElse(false)) return;
            cir.setReturnValue(FireflyBlocks.XDI8AHO_BACK_FIRE_BLOCK.get().defaultBlockState());
        }
    }

    @Inject(at = @At("HEAD"), cancellable = true,
            method = "canBePlacedAt(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Z")
    private static void canBePlacedAt(Level level, BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir){
        BlockState blockState = level.getBlockState(pos.below());
        if (blockState.is(FireflyBlockTags.BACK_PORTAL_FIRE_PLACEABLE.tagKey())){
            if (!blockState.getOptionalValue(BackPortalCoreBlock.IS_VALID).orElse(false)) return;
            cir.setReturnValue(level.getBlockState(pos).isAir());
        }
    }
}
