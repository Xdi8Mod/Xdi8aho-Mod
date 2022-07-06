package top.xdi8.mod.firefly8.item;

import net.minecraft.world.item.HoneyBottleItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.item.potion.ItemTinting;

public class TintedHoneyBottleItem extends HoneyBottleItem {

    public TintedHoneyBottleItem(Properties p_41346_) {
        super(p_41346_);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pEntityLiving) {
        var ret = super.finishUsingItem(pStack, pLevel, pEntityLiving);
        return ItemTinting.tint(ret);
    }

    @Override
    public boolean isFoil(@NotNull ItemStack pStack) {
        return true;    // players cannot differ tinted honey bottles
                        // from potions.
    }
}