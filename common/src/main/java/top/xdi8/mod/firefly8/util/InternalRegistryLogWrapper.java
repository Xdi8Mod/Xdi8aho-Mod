package top.xdi8.mod.firefly8.util;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;

@ApiStatus.Internal
public class InternalRegistryLogWrapper implements Runnable {
    private final ResourceLocation key;
    private static final Logger LOGGER = LogUtils.getLogger();

    protected InternalRegistryLogWrapper(ResourceLocation key) {
        this.key = key;
    }

    @Override
    public void run() {
        LOGGER.debug("Initialized registry {}", key);
    }

    public static InternalRegistryLogWrapper firefly8(String s) {
        return new InternalRegistryLogWrapper(new ResourceLocation("firefly8", s));
    }
}
