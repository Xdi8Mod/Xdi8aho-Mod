package org.featurehouse.mcmod.spm.util.registries;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.platform.api.hook.AccParrot;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;

@ApiStatus.Internal
public final class AnimalIngredients {
    private AnimalIngredients() {}

    public static Stream<ItemStack> configurePig(ItemStack[] stacks) {
        ArrayList<ItemStack> stackList = new ArrayList<>(Arrays.asList(stacks));
        stackList.add(SPMMain.ENCHANTED_TUBER_ITEM.get().getDefaultInstance());
        stackList.add(SPMMain.PEEL.get().getDefaultInstance());

        return stackList.stream();
    }

    public static void configureParrot() {
        Set<Item> parrotTamingIngredients = AccParrot.getTamingIngredients();
        parrotTamingIngredients.add(SPMMain.ENCHANTED_CROP_SEEDS.get());
    }

    public static Stream<ItemStack> configureChicken(ItemStack[] stacks) {
        ArrayList<ItemStack> stackList = new ArrayList<>(Arrays.asList(stacks));
        stackList.add(SPMMain.ENCHANTED_CROP_SEEDS.get().getDefaultInstance());

        return stackList.stream();
    }
}
