package top.xdi8.mod.firefly8.item.indium;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class IndiumAxeItem extends AxeItem {
    public IndiumAxeItem(Properties pProperties) {
        super(new IndiumTier(), 6.0F, -3.2F, pProperties.durability(54));
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack pStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        if (!super.hurtEnemy(pStack, pTarget, pAttacker)) return false;
        IndiumTier.dropNuggets(pStack, pTarget, pAttacker);
        return true;
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockPos pPos, @NotNull LivingEntity pEntityLiving) {
        if (!super.mineBlock(pStack, pLevel, pState, pPos, pEntityLiving)) return false;
        IndiumTier.dropNuggets(pStack, pLevel, pState, pPos, pEntityLiving);
        return true;
    }
}
