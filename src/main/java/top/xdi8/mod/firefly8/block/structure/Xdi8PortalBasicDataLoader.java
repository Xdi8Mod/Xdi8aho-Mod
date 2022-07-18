package top.xdi8.mod.firefly8.block.structure;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@Mod.EventBusSubscriber(modid = "firefly8")
public final class Xdi8PortalBasicDataLoader
        extends SimplePreparableReloadListener<Reader> {
    private static final ResourceLocation PATH_BASIC = 
            new ResourceLocation("firefly8:xdi8_portal_data.txt");
    private static final Logger LOGGER = LogUtils.getLogger();
    
    /*** Performs any reloading that can be done off-thread, such as file IO */
    @Override
    protected Reader prepare(ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        try (Resource resource = pResourceManager.getResource(PATH_BASIC)) {
            return new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            LOGGER.error("Can't load " + PATH_BASIC, e);
        }
        return Reader.nullReader();
    }

    @Override
    protected void apply(Reader reader, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        try {
            Xdi8PortalBasicData.setInstance(Xdi8PortalBasicData.readText(reader));
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error applying portal data", e);
        }
    }

    @SubscribeEvent
    public static void onLoadingData(AddReloadListenerEvent event) {
        event.addListener(new Xdi8PortalBasicDataLoader());
    }
}
