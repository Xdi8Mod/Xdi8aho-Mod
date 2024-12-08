package top.xdi8.mod.firefly8.block.symbol;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SymbolStoneNNBlock extends Block {
    public SymbolStoneNNBlock() {
        super(Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_GRAY)
                .requiresCorrectToolForDrops()
                .strength(1.5F, 8.0F)
        );
    }

    @Override
    public @NotNull String getDescriptionId() {
        return "block.firefly8.symbol_stone";
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable BlockGetter pLevel,
                                @NotNull List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        pTooltip.add(Component.translatable("block.firefly8.symbol_stone_nn"));
    }
}
