package org.featurehouse.mcfgmod.firefly8.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TintedPotionItem extends PotionItem {

    public TintedPotionItem(Properties p_42979_) {
        super(p_42979_);
    }
    
    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pEntityLiving) {
        var ret = super.finishUsingItem(pStack, pLevel, pEntityLiving);
        if (ret.is(Items.GLASS_BOTTLE)) {
            return new ItemStack(FireflyItems.TINTED_GLASS_BOTTLE.get(), ret.getCount(), 
                ret.getTag());
        }
        return ret;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
        // NO-OP: YOU CANNOT SEE ANYTHING INSIDE
    }
}
