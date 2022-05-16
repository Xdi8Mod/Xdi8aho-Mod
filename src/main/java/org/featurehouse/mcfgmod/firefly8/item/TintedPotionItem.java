package org.featurehouse.mcfgmod.firefly8.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.level.Level;

public class TintedPotionItem extends PotionItem {

    public TintedPotionItem(Properties p_42979_) {
        super(p_42979_);
    }
    
    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        var ret = super.finishUsingItem(pStack, pLevel, pEntityLiving);
        if (ret.is(Items.GLASS_BOTTLE)) {
            return new ItemStack(FireflyItems.TINTED_GLASS_BOTTLE.get(), ret.getCount(), 
                ret.getTag());
        }
        return ret;
    }

    
}
