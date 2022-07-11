package top.xdi8.mod.firefly8.item.tint;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import top.xdi8.mod.firefly8.block.FireflyBlockTags;
import top.xdi8.mod.firefly8.entity.FireflyEntity;
import top.xdi8.mod.firefly8.entity.FireflyEntityTypes;

public class TintedFireflyBottleItem extends Item {
    private static final Logger LOGGER = LogUtils.getLogger();
    public TintedFireflyBottleItem(Properties pProperties) {
        super(pProperties);
    }
    /* FIREFLY BOTTLING START */

    @Override
    public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack pStack, @NotNull Player pPlayer, @NotNull LivingEntity pTarget, @NotNull InteractionHand pUsedHand) {
        if (!(pTarget instanceof FireflyEntity firefly)) {
            return InteractionResult.PASS;
        }
        Level level = pPlayer.getLevel();
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        stack.shrink(1);
        level.playSound(pPlayer, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.BOTTLE_FILL,
                SoundSource.NEUTRAL, 1.0F, 1.0F);

        firefly.setInBottleTime(level.getGameTime());
        CompoundTag targetTags = new CompoundTag();
        firefly.save(targetTags);
        final CompoundTag rootTag = stack.getOrCreateTag();
        ListTag fireflyList;
        if (rootTag.contains("Fireflies", 9))
            fireflyList = rootTag.getList("Fireflies", 10);
        else {
            fireflyList = new ListTag();
            rootTag.put("Fireflies", fireflyList);
        }
        fireflyList.add(targetTags);
        if (stack.isEmpty()) {
            pPlayer.setItemInHand(pUsedHand, stack);
        } else if (!pPlayer.getInventory().add(stack)) {
            pPlayer.drop(stack, false);
        }
        firefly.remove(Entity.RemovalReason.DISCARDED);
        return InteractionResult.SUCCESS;
    }

    /* FIREFLY BOTTLING END */

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext pContext) {
        Level level = pContext.getLevel();
        if (level.isClientSide()) return InteractionResult.SUCCESS;

        BlockPos clickedPos = pContext.getClickedPos();
        final BlockState state = level.getBlockState(clickedPos);
        if (state.is(FireflyBlockTags.FIREFLIES_CAN_RELEASE)) {
            var airPos = getNearAirPos(level, clickedPos);
            if (airPos.isEmpty()) {
                LOGGER.debug("No space for spawning from {}", clickedPos);
                return InteractionResult.PASS;
            }
            Player player = pContext.getPlayer();
            ItemStack itemStack = pContext.getItemInHand();
            if (player == null) return InteractionResult.PASS;
            ListTag fireflyList = itemStack.getOrCreateTag().getList("Fireflies", 10);
            CompoundTag fireflyTag = fireflyList.getCompound(fireflyList.size() - 1);
            EntityType<FireflyEntity> fireflyEntityType = FireflyEntityTypes.FIREFLY.get();
            FireflyEntity fireflyEntity = (FireflyEntity) fireflyEntityType.spawn((ServerLevel) level, itemStack, player, airPos.get(), MobSpawnType.BUCKET, true, false);
            if (fireflyEntity == null) {
                LOGGER.error("Null firefly spawn");
                return InteractionResult.PASS;
            }
            fireflyEntity.load(fireflyTag);
            fireflyEntity.setOutOfBottleTime(level.getGameTime());
            if (fireflyEntity.getOutOfBottleTime() - fireflyEntity.getInBottleTime() >= 24000L){
                // 20 minutes
                fireflyEntity.setOwnerUUID(player.getUUID());
            }
            fireflyList.remove(fireflyTag);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    public static java.util.Optional<BlockPos> getNearAirPos(Level level, BlockPos blockPos) {
        return ALLOWED_SPAWN_POS.stream().map(t -> t.apply(blockPos))
                .filter(p -> level.getBlockState(p).isAir()).findFirst();
    }

    private static final java.util.List<java.util.function.UnaryOperator<BlockPos>> ALLOWED_SPAWN_POS =
            com.google.common.collect.ImmutableList.of(
                    BlockPos::above, //BlockPos::below,
                    BlockPos::east, BlockPos::south,
                    BlockPos::west, BlockPos::north
            );
}
