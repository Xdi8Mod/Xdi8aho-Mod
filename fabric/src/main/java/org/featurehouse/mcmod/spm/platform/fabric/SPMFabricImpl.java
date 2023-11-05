package org.featurehouse.mcmod.spm.platform.fabric;

import dev.architectury.registry.ReloadListenerRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.advancement.BalancedDietHelper;
import org.featurehouse.mcmod.spm.platform.api.reg.RegistryContainer;
import org.featurehouse.mcmod.spm.resource.magicalenchantment.MagicalEnchantmentLoader;
import top.xdi8.mod.firefly8.advancement.AdvancementLoadingContext;

public class SPMFabricImpl implements ModInitializer {
    private static final ResourceLocation BALANCED_DIET = new ResourceLocation("husbandry/balanced_diet");

    @Override
    public void onInitialize() {
        SPMMain.getLogger().debug("Loading SPM...");
        RegistryContainer.of(SPMMain.MODID).subscribeModBus();
        AdvancementLoadingContext.EVENT.register(ctx -> {
            if (BALANCED_DIET.equals(ctx.id)) {
                BalancedDietHelper.setupCriteria(ctx);
            }
        });
        SPMMain.getInstance().onInitialize();
        ReloadListenerRegistry.register(PackType.SERVER_DATA, new MagicalEnchantmentLoader());
    }
}
