package org.featurehouse.mcmod.spm.mixin.acc;

import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(Parrot.class)
public interface ParrotEntityAccessor {
    @Accessor("TAME_FOOD")
    @Mutable
    static Set<Item> getTamingIngredients() {
        throw new AssertionError("Mixin");
    }
}
