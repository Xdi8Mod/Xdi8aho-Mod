package org.featurehouse.mcmod.spm.mixin.ingredient;

import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.item.crafting.Ingredient;
import org.featurehouse.mcmod.spm.util.ItemStacks;
import org.featurehouse.mcmod.spm.util.registries.AnimalIngredients;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("unused")
@Mixin(Pig.class)
abstract class PigEntityMixin {
    @Shadow @Final @Mutable
    private static Ingredient FOOD_ITEMS;

    static {
        FOOD_ITEMS = ItemStacks.expandIngredient(FOOD_ITEMS, AnimalIngredients.pigFood());
    }
}
