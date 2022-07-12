package top.xdi8.mod.firefly8.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;

public class Xdi8ahoPortalTopBlock extends Block {
    public static final BooleanProperty IS_ACTIVATED =
            BooleanProperty.create("activated");

    public Xdi8ahoPortalTopBlock() {
        super(Properties.of(Material.STONE)
                .strength(8F, 800F)//TODO
                .requiresCorrectToolForDrops()
                .lightLevel(bs -> bs.getValue(IS_ACTIVATED) ? 15 : 0)
        );
        registerDefaultState(getStateDefinition().any().setValue(IS_ACTIVATED, Boolean.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(IS_ACTIVATED);
    }
}
