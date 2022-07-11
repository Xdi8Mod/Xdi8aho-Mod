package top.xdi8.mod.firefly8.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;

public class Xdi8ahoPortalCoreBlock extends Block {
    public Xdi8ahoPortalCoreBlock() {
        super(Properties.of(Material.STONE)
                .strength(10F, 1200F)
                .requiresCorrectToolForDrops()
                .lightLevel(bs -> bs.getValue(FIREFLY_COUNT) * 3)
        );
    }

    public static final IntegerProperty FIREFLY_COUNT =
            IntegerProperty.create("fireflies", 0, 5);

}
