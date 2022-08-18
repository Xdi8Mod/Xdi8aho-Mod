package top.xdi8.mod.firefly8.fabric;

import dev.architectury.registry.ReloadListenerRegistry;
import net.fabricmc.api.ModInitializer;
import org.featurehouse.mcmod.spm.SPMMain;
import top.xdi8.mod.firefly8.Firefly8;

public class Firefly8Fabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Firefly8.init();
        SPMMain.getInstance().onInitialize();
        Firefly8.commonSetup();
    }
}
