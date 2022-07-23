package top.xdi8.mod.firefly8.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;

public class IndiumOreBlock extends Block {
    public IndiumOreBlock() {
        super(Properties.of(Material.STONE)
                .strength(3.0F, 3.0F)
                .requiresCorrectToolForDrops()
        );
    }
}
