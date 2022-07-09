package org.featurehouse.mcmod.spm.items;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.featurehouse.mcmod.spm.util.annotation.StableApi;
import org.featurehouse.mcmod.spm.platform.api.reg.PlatformRegister;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Supplier;

@StableApi
public class AliasedEnchantedItem extends ItemNameBlockItem {
    public AliasedEnchantedItem(Block block, Properties settings) {
        super(block, settings);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @ApiStatus.Internal
    public static Supplier<Item> of(String id, Supplier<Block> original) {
        return of(id, original, CreativeModeTab.TAB_MISC);
    }

    @ApiStatus.Internal
    public static Supplier<Item> of(String id, Supplier<Block> original, CreativeModeTab itemGroup) {
        Supplier<Item> sup = ()->new AliasedEnchantedItem(original.get(), new Item.Properties().tab(itemGroup));
        return PlatformRegister.getInstance().item(id, sup);
    }

    @ApiStatus.Internal
    public static Supplier<Item> ofMiscFood(String id, Supplier<Block> original, FoodProperties foodComponent) {
        Supplier<Item> sup = ()->new AliasedEnchantedItem(original.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC).food(foodComponent));
        return PlatformRegister.getInstance().item(id, sup);
    }
}
