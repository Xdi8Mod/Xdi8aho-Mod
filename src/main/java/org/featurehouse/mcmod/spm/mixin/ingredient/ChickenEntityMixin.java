package org.featurehouse.mcmod.spm.mixin.ingredient;

import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CompoundIngredient;
import org.featurehouse.mcmod.spm.util.registries.AnimalIngredients;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("unused")
@Mixin(Chicken.class)
abstract class ChickenEntityMixin {
    @Shadow @Final @Mutable
    private static Ingredient FOOD_ITEMS;

    static {
        FOOD_ITEMS = CompoundIngredient.of(FOOD_ITEMS, Ingredient.of(AnimalIngredients.chickenFood()));
    }
}
