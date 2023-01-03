package top.xdi8.mod.firefly8.fabric;

import net.fabricmc.api.ClientModInitializer;
import top.xdi8.mod.firefly8.FireflyClientSetup;

public class FireflyClientForge implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        new FireflyClientSetup().run();
    }
}
