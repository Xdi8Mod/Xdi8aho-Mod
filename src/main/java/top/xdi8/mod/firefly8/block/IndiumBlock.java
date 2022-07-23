package top.xdi8.mod.firefly8.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;

public class IndiumBlock extends Block {
    public IndiumBlock() {
        super(Properties.of(Material.STONE)
                .strength(1.0F, 6.0F)
                .requiresCorrectToolForDrops()
        );
    }
}
