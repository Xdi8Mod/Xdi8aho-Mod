package org.featurehouse.mcmod.spm.platform.api.client.fabric;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

@Deprecated
public class BlockRenderTypesImpl {
    public static void register(RenderType type, Supplier<Block> block) {
        BlockRenderLayerMap.INSTANCE.putBlock(block.get(), type);
    }
}
