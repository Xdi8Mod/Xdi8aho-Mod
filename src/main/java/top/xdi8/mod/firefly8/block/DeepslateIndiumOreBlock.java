package top.xdi8.mod.firefly8.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;

public class DeepslateIndiumOreBlock extends Block {
    public DeepslateIndiumOreBlock() {
        super(Properties.of(Material.STONE)
                .strength(4.5F, 3.0F)
                .requiresCorrectToolForDrops()
        );
    }
}
