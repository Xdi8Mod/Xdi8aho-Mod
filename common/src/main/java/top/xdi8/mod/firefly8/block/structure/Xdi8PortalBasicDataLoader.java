package top.xdi8.mod.firefly8.block.structure;

import com.mojang.logging.LogUtils;
import io.github.qwerty770.mcmod.xdi8.api.ResourceLocationTool;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.featurehouse.mcmod.spm.platform.api.resource.KeyedReloadListener;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public final class Xdi8PortalBasicDataLoader
        extends SimplePreparableReloadListener<Reader>
        implements KeyedReloadListener {
    private static final ResourceLocation PATH_BASIC = 
            ResourceLocationTool.create("firefly8", "xdi8_portal_data.txt");
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

    @Override
    public ResourceLocation getId() {
        return ResourceLocationTool.create("firefly8", "portal_base");
    }
}
