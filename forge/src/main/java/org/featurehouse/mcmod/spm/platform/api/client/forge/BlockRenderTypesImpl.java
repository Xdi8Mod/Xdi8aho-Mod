package org.featurehouse.mcmod.spm.platform.api.client.forge;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class BlockRenderTypesImpl {
    public static void register(RenderType type, Supplier<Block> block) {
        net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(block.get(), type);
    }
}
