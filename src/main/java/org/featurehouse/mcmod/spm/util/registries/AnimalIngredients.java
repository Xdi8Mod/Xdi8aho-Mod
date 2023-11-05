package org.featurehouse.mcmod.spm.util.registries;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.platform.api.reg.PlatformRegister;
import org.jetbrains.annotations.ApiStatus;

import java.util.Set;

@ApiStatus.Internal
public final class AnimalIngredients {
    private AnimalIngredients() {}

    public static void configureParrot() {
        Set<Item> parrotTamingIngredients = net.minecraft.world.entity.animal.Parrot.TAME_FOOD;
        parrotTamingIngredients.add(SPMMain.ENCHANTED_CROP_SEEDS.get());
    }

    public static final TagKey<Item> CHICKEN_EXTRA_FOOD = PlatformRegister.spm().itemTag("chicken_extra_food");
    //public static final TagKey<Item> PARROT_EXTRA_FOOD = PlatformRegister.spm().itemTag("parrot_extra_food");
    public static final TagKey<Item> PIG_EXTRA_FOOD = PlatformRegister.spm().itemTag("pig_extra_food");
}
