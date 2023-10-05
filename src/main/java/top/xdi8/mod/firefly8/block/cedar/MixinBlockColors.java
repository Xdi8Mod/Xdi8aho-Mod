package top.xdi8.mod.firefly8.block.cedar;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.FoliageColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import top.xdi8.mod.firefly8.block.FireflyBlocks;

@Mixin(BlockColors.class)
public class MixinBlockColors {
    // Add block colors for cedar leaves
    @Inject(
            at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD,
            method = "createDefault()Lnet/minecraft/client/color/block/BlockColors;"
    )
    public void registerLeavesColors(CallbackInfo ci, BlockColors blockColors) {
        blockColors.register((blockState, blockAndTintGetter, blockPos, i) -> {
            if (blockAndTintGetter == null || blockPos == null) {
                return FoliageColor.getDefaultColor();
            }
            return BiomeColors.getAverageFoliageColor(blockAndTintGetter, blockPos);
        }, FireflyBlocks.CEDAR_LEAVES.get());
    }
}
