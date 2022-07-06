package top.xdi8.mod.firefly8.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import top.xdi8.mod.firefly8.item.potion.ItemTinting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TintedPotionItem extends PotionItem {

    public TintedPotionItem(Properties p_42979_) {
        super(p_42979_);
    }
    
    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pEntityLiving) {
        return ItemTinting.tint(super.finishUsingItem(pStack, pLevel, pEntityLiving));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
        // NO-OP: YOU CANNOT SEE ANYTHING INSIDE
    }

    @Override
    public boolean isFoil(@NotNull ItemStack pStack) {
        return true;    // Water bottles can't be seen but weigh
    }

    @Override
    public @NotNull String getDescriptionId(@NotNull ItemStack pStack) {
        return getDescriptionId();  // as default
    }
}
