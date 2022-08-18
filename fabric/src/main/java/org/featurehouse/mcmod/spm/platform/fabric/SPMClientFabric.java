package org.featurehouse.mcmod.spm.platform.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import org.featurehouse.mcmod.spm.client.SPMClient;
import org.featurehouse.mcmod.spm.platform.api.client.ColorProviders;

public class SPMClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        new SPMClient().onInitializeClient();
        SPMClient.initColorProviders();

        var view = ColorProviders.getItem().view();
        view.forEach((itemSupplier, itemColor) ->
                ColorProviderRegistry.ITEM.register(itemColor, itemSupplier.get()));
        var view2 = ColorProviders.getBlock().view();
        view2.forEach((blockSupplier, blockColor) ->
                ColorProviderRegistry.BLOCK.register(blockColor, blockSupplier.get()));
    }
}
