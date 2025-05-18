package top.xdi8.mod.firefly8.block;

import com.mojang.serialization.MapCodec;
import io.github.qwerty770.mcmod.xdi8.entity.AbstractBlockWithEntity;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.block.entity.BackPortalCoreBlockEntity;
import top.xdi8.mod.firefly8.block.entity.FireflyBlockEntityTypes;

public class BackPortalCoreBlock extends AbstractBlockWithEntity<BackPortalCoreBlockEntity> {
    public static final BooleanProperty IS_VALID = BooleanProperty.create("valid");

    public BackPortalCoreBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(IS_VALID, false));
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(BackPortalCoreBlock::new);
    }

    @Override
    protected boolean blockEntityPredicate(@NotNull BlockEntity blockEntity) {
        return blockEntity instanceof BackPortalCoreBlockEntity;
    }

    @Override
    protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(IS_VALID);
    }

    @Override
    public @NotNull BlockEntityType<BackPortalCoreBlockEntity> getBlockEntityType() {
        return FireflyBlockEntityTypes.BACK_PORTAL_CORE.get();
    }

    @Override
    public @NotNull BackPortalCoreBlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new BackPortalCoreBlockEntity(pPos, pState);
    }

    @Override
    protected @NotNull InteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        BlockPos pos1 = pos.above();
        if (stack.is(Items.FLINT_AND_STEEL) && state.getValue(IS_VALID) && level.getBlockState(pos1).isAir()) {
            level.playSound(player, pos1, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
            level.setBlock(pos1, FireflyBlocks.XDI8AHO_BACK_FIRE_BLOCK.get().defaultBlockState(), 11);
            level.gameEvent(player, GameEvent.BLOCK_PLACE, pos1);
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, pos1, stack);
                stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
            }
            return InteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }
}
