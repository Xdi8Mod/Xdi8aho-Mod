package top.xdi8.mod.firefly8.item.tint;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.entity.FireflyEntity;

public class TintedFireflyBottleItem extends Item {
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
        // stack.getTagElement returns a CompoundTag object instead of ListTag
        // CompoundTag fireflies = stack.getTagElement("Fireflies");
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
    public @NotNull InteractionResult useOn(@NotNull UseOnContext pContext){
        // TODO: release the fireflies in the bottle
        return InteractionResult.PASS;
    }
}
