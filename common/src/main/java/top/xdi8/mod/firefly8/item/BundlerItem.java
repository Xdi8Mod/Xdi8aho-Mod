package top.xdi8.mod.firefly8.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BundlerItem extends Item {
    public BundlerItem(Properties properties) {
        super(properties);
    }

    protected List<ItemStack> readItemsInside(@NotNull ItemStack stack) {
        return stack.get(FireflyDataComponentTypes.STORED_ITEMS.get());
    }

    @Override
    public @NotNull InteractionResult use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        var container = readItemsInside(pPlayer.getItemInHand(pUsedHand));
        for (var itemStack : container) {
            if (!pPlayer.addItem(itemStack)) {
                pPlayer.drop(itemStack, false);
            }
        }
        return pLevel.isClientSide() ? InteractionResult.SUCCESS.heldItemTransformedTo(ItemStack.EMPTY) : InteractionResult.CONSUME.heldItemTransformedTo(ItemStack.EMPTY);
    }
}
