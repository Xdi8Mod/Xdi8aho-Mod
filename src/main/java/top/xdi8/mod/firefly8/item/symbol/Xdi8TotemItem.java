package top.xdi8.mod.firefly8.item.symbol;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Xdi8TotemItem extends Item {
    public Xdi8TotemItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        // TODO
    }

    @Override
    public boolean isFoil(@NotNull ItemStack pStack) {
        return super.isFoil(pStack);//TODO
    }
}
