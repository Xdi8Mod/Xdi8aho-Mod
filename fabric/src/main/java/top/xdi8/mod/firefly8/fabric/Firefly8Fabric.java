package top.xdi8.mod.firefly8.fabric;

import net.fabricmc.api.ModInitializer;
import top.xdi8.mod.firefly8.Firefly8;

public class Firefly8Fabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Firefly8.init();
        Firefly8.commonSetup();
    }
}
