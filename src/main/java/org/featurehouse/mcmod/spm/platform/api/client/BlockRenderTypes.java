package org.featurehouse.mcmod.spm.platform.api.client;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * @apiNote This must be wrapped in a client initialization event
 */
public final class BlockRenderTypes {
    public static void register(RenderType type, Collection<Supplier<Block>> blocks) {
        for (var b : blocks)
            register(type, b);
    }

    // Forge: hooks
    public static void register(RenderType type, Supplier<Block> block) {
        net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(block.get(), type);
    }
}
