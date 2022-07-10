package org.featurehouse.mcmod.spm.util.inventory;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import static org.featurehouse.mcmod.spm.SPMMain.PEEL;

public interface PeelInserter {
    static void run(Player player) {
        Inventory inventory = player.getInventory();
        if (!inventory.add(new ItemStack(PEEL.get())))
            player.drop(new ItemStack(PEEL.get()), false);
    }
}
