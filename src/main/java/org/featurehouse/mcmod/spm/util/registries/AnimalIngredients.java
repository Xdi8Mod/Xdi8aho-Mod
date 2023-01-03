package org.featurehouse.mcmod.spm.util.registries;

import net.minecraft.world.item.Item;
import org.featurehouse.mcmod.spm.SPMMain;
import org.jetbrains.annotations.ApiStatus;

import java.util.Set;

@ApiStatus.Internal
public final class AnimalIngredients {
    private AnimalIngredients() {}

    public static Item[] pigFood() {
        return new Item[] { SPMMain.ENCHANTED_TUBER_ITEM.get(), SPMMain.PEEL.get() };
    }

    public static void configureParrot() {
        Set<Item> parrotTamingIngredients = net.minecraft.world.entity.animal.Parrot.TAME_FOOD;
        parrotTamingIngredients.add(SPMMain.ENCHANTED_CROP_SEEDS.get());
    }

    public static Item[] chickenFood() {
        return new Item[] { SPMMain.ENCHANTED_CROP_SEEDS.get() };
    }
}
