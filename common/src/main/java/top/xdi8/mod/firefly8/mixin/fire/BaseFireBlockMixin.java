package top.xdi8.mod.firefly8.mixin.fire;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.xdi8.mod.firefly8.block.FireflyBlockTags;
import top.xdi8.mod.firefly8.block.FireflyBlocks;

@Mixin(BaseFireBlock.class)
public class BaseFireBlockMixin {
    @Inject(
            at = @At("HEAD"), cancellable = true,
            method = "getState(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"
    )
    private static void onGetState(BlockGetter level, BlockPos pPos, CallbackInfoReturnable<BlockState> cir) {
        if (level.getBlockState(pPos.below()).is(FireflyBlockTags.BACK_PORTAL_FIRE_PLACEABLE.tagKey())) {
            cir.setReturnValue(FireflyBlocks.XDI8AHO_BACK_FIRE_BLOCK.get().defaultBlockState());
        }
    }
}
