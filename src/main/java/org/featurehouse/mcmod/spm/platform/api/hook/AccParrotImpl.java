package org.featurehouse.mcmod.spm.platform.api.hook;

import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.item.Item;

import java.util.Set;

final class AccParrotImpl {
    static Set<Item> getParrotIngredients() {
        return Parrot.TAME_FOOD;
    }
}
