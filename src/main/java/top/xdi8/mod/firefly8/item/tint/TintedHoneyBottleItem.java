package top.xdi8.mod.firefly8.item.tint;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.item.FireflyItems;

public class TintedHoneyBottleItem extends Item {
    public TintedHoneyBottleItem(Properties p_41346_) {
        super(p_41346_);
    }

    /**
     * Overwriting it cuz mixins should be avoided.
     * @see HoneyBottleItem#finishUsingItem(ItemStack, Level, LivingEntity)
     */
    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pEntityLiving) {
        super.finishUsingItem(pStack, pLevel, pEntityLiving);
        if (pEntityLiving instanceof ServerPlayer serverplayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayer, pStack);
            serverplayer.awardStat(Stats.ITEM_USED.get(this));
        }

        if (!pLevel.isClientSide) {
            pEntityLiving.removeEffect(MobEffects.POISON);
        }

        if (pStack.isEmpty()) {
            return new ItemStack(FireflyItems.TINTED_GLASS_BOTTLE.get());
        } else {
            if (pEntityLiving instanceof Player player && !((Player)pEntityLiving).getAbilities().instabuild) {
                ItemStack itemstack = new ItemStack(FireflyItems.TINTED_GLASS_BOTTLE.get());
                if (!player.getInventory().add(itemstack)) {
                    player.drop(itemstack, false);
                }
            }

            return pStack;
        }
    }

    @Override
    public boolean isFoil(@NotNull ItemStack pStack) {
        return true;    // players cannot differ tinted honey bottles
                        // from potions.
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getUseDuration(@NotNull ItemStack pStack) {
        return 40;
    }

    /**
     * returns the action that specifies what animation to play when the items are being used
     */
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pStack) {
        return UseAnim.DRINK;
    }

    public @NotNull SoundEvent getDrinkingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    public @NotNull SoundEvent getEatingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pHand) {
        return ItemUtils.startUsingInstantly(pLevel, pPlayer, pHand);
    }
}
