package top.xdi8.mod.firefly8.item.indium;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.item.FireflyItems;

public class IndiumSwordItem extends SwordItem {
    public IndiumSwordItem(Properties pProperties) {
        super(new IndiumTier(), 3, -2.4F, pProperties.durability(36));
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        pStack.hurtAndBreak(1, pAttacker, (p_43296_) -> p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        Level level = pAttacker.getLevel();
        if (pAttacker instanceof Player){
            if (level.isClientSide()) return true;
            ItemStack nugget = new ItemStack(FireflyItems.INDIUM_NUGGET.get());
            if (!((Player) pAttacker).getInventory().add(nugget)) {
                ((Player) pAttacker).drop(nugget, true);
            }
        }
        return true;
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack pStack, @NotNull Level pLevel, BlockState pState, @NotNull BlockPos pPos, @NotNull LivingEntity pEntityLiving) {
        if (pState.getDestroySpeed(pLevel, pPos) != 0.0F) {
            pStack.hurtAndBreak(2, pEntityLiving, (p_43276_) -> p_43276_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            if (pEntityLiving instanceof Player){
                if (pLevel.isClientSide() || pLevel.getRandom().nextDouble() > 0.49) return true;
                ItemStack nugget = new ItemStack(FireflyItems.INDIUM_NUGGET.get());
                if (!((Player) pEntityLiving).getInventory().add(nugget)) {
                    ((Player) pEntityLiving).drop(nugget, true);
                }
            }
        }
        return true;
    }
}
