package top.xdi8.mod.firefly8.fabric;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import top.xdi8.mod.firefly8.Firefly8;
import top.xdi8.mod.firefly8.network.FireflyNetwork;

public class Firefly8Fabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Firefly8.activateRegistries();
        Firefly8.init();
        Firefly8.commonSetup();
        if (FabricLoader.getInstance().getEnvironmentType().equals(EnvType.CLIENT)){
            FireflyNetwork.registerClientNetwork();
        }
        FireflyNetwork.registerServerNetwork();
    }
}
