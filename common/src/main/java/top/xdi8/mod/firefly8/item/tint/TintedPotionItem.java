package top.xdi8.mod.firefly8.item.tint;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.item.FireflyItems;

import java.util.List;

public class TintedPotionItem extends PotionItem {
    public TintedPotionItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pEntityLiving) {
        Player player = pEntityLiving instanceof Player ? (Player) pEntityLiving : null;
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, pStack);
        }

        if (!pLevel.isClientSide) {
            PotionContents contents = pStack.get(DataComponents.POTION_CONTENTS);
            if (contents == null) return pStack;
            for (MobEffectInstance mobeffectinstance : contents.getAllEffects()) {
                if (mobeffectinstance.getEffect().value().isInstantenous()) {
                    mobeffectinstance.getEffect().value().applyInstantenousEffect((ServerLevel) pLevel, player, player, pEntityLiving, mobeffectinstance.getAmplifier(), 1.0D);
                } else {
                    pEntityLiving.addEffect(new MobEffectInstance(mobeffectinstance));
                }
            }
        }

        if (player != null) {
            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) {
                pStack.shrink(1);
            }
        }

        if (player == null || !player.getAbilities().instabuild) {
            if (pStack.isEmpty()) {
                return new ItemStack(FireflyItems.TINTED_GLASS_BOTTLE.get());
            }

            if (player != null) {
                player.getInventory().add(new ItemStack(FireflyItems.TINTED_GLASS_BOTTLE.get()));
            }
        }

        pLevel.gameEvent(pEntityLiving, GameEvent.DRINK, pEntityLiving.position());
        return pStack;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        // NO-OP: YOU CANNOT SEE ANYTHING INSIDE
    }

    @Override
    public boolean isFoil(@NotNull ItemStack pStack) {
        return true;    // Water bottles can't be seen but weigh
    }
}
