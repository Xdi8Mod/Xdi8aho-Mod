package org.featurehouse.mcmod.spm.items;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.featurehouse.mcmod.spm.util.annotation.StableApi;
import org.featurehouse.mcmod.spm.platform.api.reg.PlatformRegister;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Supplier;

@StableApi
public class EnchantedBlockItem extends BlockItem {
    public EnchantedBlockItem(Block block, Properties settings) {
        super(block, settings);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @ApiStatus.Internal
    public static Supplier<Item> of(String id, Supplier<Block> original, Supplier<Properties> settings) {
        return PlatformRegister.spm().item(id, ()->new EnchantedBlockItem(original.get(), settings.get()));
    }
}
