package org.featurehouse.mcmod.spm.blocks;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.blocks.entities.MagicCubeBlockEntity;
import org.featurehouse.mcmod.spm.lib.block.entity.TickableContainerEntityBlock;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class MagicCubeBlock extends TickableContainerEntityBlock<MagicCubeBlockEntity> {
    public static BooleanProperty ACTIVATED = BooleanProperty.create("activated");

    @Override
    public List<ResourceLocation> incrementWhileOnUse(BlockState state, Level world, BlockPos pos, ServerPlayer serverPlayerEntity, InteractionHand hand, BlockHitResult blockHitResult) {
        return ImmutableList.of(SPMMain.INTERACT_WITH_MAGIC_CUBE.get());
    }

    @Override
    protected boolean blockEntityPredicate(BlockEntity blockEntity) {
        return blockEntity instanceof MagicCubeBlockEntity;
    }

    @Override
    public BlockEntityType<MagicCubeBlockEntity> getBlockEntityType() {
        return SPMMain.MAGIC_CUBE_BLOCK_ENTITY_TYPE.get();
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!world.isClientSide) {
            if (state.getBlock() instanceof MagicCubeBlock && !state.getValue(ACTIVATED)) return InteractionResult.PASS;
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof MagicCubeBlockEntity && state.getBlock() instanceof MagicCubeBlock && state.getValue(ACTIVATED)) {
                player.openMenu((MenuProvider) blockEntity);
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.SUCCESS;
    }

    public MagicCubeBlock(Properties settings) {
        super(settings);
        registerDefaultState(this.getStateDefinition().any().setValue(ACTIVATED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVATED);
    }

    @Override
    public @NotNull MagicCubeBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MagicCubeBlockEntity(pos, state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        if (pState.getValue(ACTIVATED) && pRandom.nextInt(32) == 0) {
            pLevel.playSound(null, pPos, SPMMain.MAGIC_CUBE_AMBIENT.get(),
                    SoundSource.BLOCKS, 1.0f, 1.0f);
        }
    }
}
