package org.featurehouse.mcmod.spm.platform.api.hook;

import net.minecraft.world.item.Item;

import java.util.Set;

public interface AccParrot {
    static Set<Item> getTamingIngredients() {
        return net.minecraft.world.entity.animal.Parrot.TAME_FOOD;
    }
}
