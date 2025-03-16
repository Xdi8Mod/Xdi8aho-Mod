package top.xdi8.mod.firefly8.fabric;

import net.fabricmc.api.ClientModInitializer;
import top.xdi8.mod.firefly8.Firefly8Client;

public class Firefly8ClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        new Firefly8Client().run();
        Firefly8Client.registerRenderTypes();
        Firefly8Client.registerParticles();
    }
}
