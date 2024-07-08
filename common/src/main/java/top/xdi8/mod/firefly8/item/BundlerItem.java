package top.xdi8.mod.firefly8.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BundlerItem extends Item {
    public BundlerItem() {
        super(new Properties().stacksTo(1));
    }

    protected List<ItemStack> readItemsInside(@NotNull ItemStack stack) {
        final CompoundTag root = stack.getOrCreateTag();
        var container = new SimpleContainer(54);
        if (root.contains("StoredItems", 9)) {
            container.fromTag(root.getList("StoredItems", 10));
        }
        return container.removeAllItems();
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        var container = readItemsInside(pPlayer.getItemInHand(pUsedHand));
        for (var itemStack : container) {
            if (!pPlayer.addItem(itemStack)) {
                pPlayer.drop(itemStack, false);
            }
        }
        return InteractionResultHolder.sidedSuccess(ItemStack.EMPTY, pLevel.isClientSide());
    }
}
