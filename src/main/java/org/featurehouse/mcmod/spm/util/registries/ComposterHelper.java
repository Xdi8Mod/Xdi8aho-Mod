package org.featurehouse.mcmod.spm.util.registries;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.util.objsettings.sweetpotato.SweetPotatoComponent;
import org.featurehouse.mcmod.spm.util.objsettings.sweetpotato.SweetPotatoStatus;
import org.featurehouse.mcmod.spm.util.objsettings.sweetpotato.SweetPotatoType;
import org.jetbrains.annotations.ApiStatus;

public final class ComposterHelper {
    private ComposterHelper() {}

    @ApiStatus.Internal
    public static void register() {
        registerCompostableItem(0.3f, SPMMain.PEEL.get());
        registerCompostableItem(0.3f, SPMMain.ENCHANTED_SAPLING_ITEM.get());
        registerCompostableItem(0.3f, SPMMain.ENCHANTED_LEAVES_ITEM.get());
        registerCompostableItem(0.3f, SPMMain.ENCHANTED_CROP_SEEDS.get());
        registerCompostableItem(0.65f, SPMMain.ENCHANTED_CROP_ITEM.get());
        registerCompostableItem(0.65f, SPMMain.ENCHANTED_TUBER_ITEM.get());


        for (SweetPotatoType type: SweetPotatoType.values()) {
            for (SweetPotatoStatus status: SweetPotatoStatus.values()) {
                SweetPotatoComponent component = type.getComponent(status);
                if (component != null) {
                    component.registerCompostableItem(type, status);
                    component.registerGrindableItem(type, status);
                }
            }
        }
    }

    public static void registerCompostableItem(float levelIncreaseChance, ItemLike item) {
        ComposterBlock.COMPOSTABLES.put(item, levelIncreaseChance);
    }
}
