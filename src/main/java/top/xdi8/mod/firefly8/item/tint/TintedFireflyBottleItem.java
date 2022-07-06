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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
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
        // TODO: add targetTags to the ItemStack
        assert stack.getTag() != null;
        ListTag fireflyList = stack.getTag().getList("Fireflies", 9);
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
        // TODO: release the fireflies in the bottle
        Level level = pContext.getLevel();
        BlockPos clickedPos = pContext.getClickedPos();
        Block usedOnBlock = level.getBlockState(clickedPos).getBlock();
        if (usedOnBlock == Blocks.GRASS_BLOCK || usedOnBlock == Blocks.GRASS) {
            BlockPos airPos = getNearAirPos(level, clickedPos);
            if (airPos == null) {
                LOGGER.debug("No space for spawning");
                return InteractionResult.FAIL;
            }
            Player player = pContext.getPlayer();
            ItemStack itemStack = pContext.getItemInHand();
            assert player != null;
            assert itemStack.getTag() != null;
            ListTag fireflyList = itemStack.getTag().getList("Fireflies", 9);
            CompoundTag fireflyTag = fireflyList.getCompound(fireflyList.size() - 1);
            EntityType<FireflyEntity> fireflyEntityType = FireflyEntityTypes.FIREFLY.get();
            FireflyEntity fireflyEntity = (FireflyEntity) fireflyEntityType.spawn((ServerLevel) level, itemStack, player, airPos, MobSpawnType.BUCKET, true, false);
            assert fireflyEntity != null;
            fireflyEntity.load(fireflyTag);
            fireflyEntity.setOutOfBottleTime(level.getGameTime());
            if (fireflyEntity.getOutOfBottleTime() - fireflyEntity.getInBottleTime() >= 24000L){
                // 20 minutes
                fireflyEntity.setOwnerUUID(player.getUUID());
            }
            fireflyList.remove(fireflyTag);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public static BlockPos getNearAirPos(Level level, BlockPos blockPos) {
        if (level.getBlockState(blockPos.above()).isAir()) {
            return blockPos.above();
        } else if (level.getBlockState(blockPos.below()).isAir()) {
            return blockPos.below();
        } else if (level.getBlockState(blockPos.east()).isAir()) {
            return blockPos.east();
        } else if (level.getBlockState(blockPos.west()).isAir()) {
            return blockPos.west();
        } else if (level.getBlockState(blockPos.south()).isAir()) {
            return blockPos.south();
        } else if (level.getBlockState(blockPos.north()).isAir()) {
            return blockPos.north();
        }
        return null;
    }
}
