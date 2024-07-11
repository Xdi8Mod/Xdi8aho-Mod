package org.featurehouse.mcmod.spm.platform.fabric;

import net.fabricmc.api.ClientModInitializer;
import org.featurehouse.mcmod.spm.client.SPMClient;

public class SPMClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        new SPMClient().run();  // 1.21: Moved item/block color registry to SPMClient.run()
    }
}
