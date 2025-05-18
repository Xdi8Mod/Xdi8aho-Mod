package top.xdi8.mod.fonts.forge;

import org.objectweb.asm.tree.ClassNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class LoadFontsConfigWrapper implements IMixinConfigPlugin {

    private boolean isMuiLoaded;
    private static final Logger LOGGER = LoggerFactory.getLogger("Xdi8Fonts CoreMod");

    /**
     * Called after the plugin is instantiated, do any setup here.
     *
     * @param mixinPackage The mixin root package from the config
     */
    @Override
    public void onLoad(String mixinPackage) {
        try {
            // Detect annotation, to avoid conflicts with other mods that
            // wanna mixin any functional class.
            Class.forName("icyllis.modernui.annotation.MainThread", false,
                    LoadFontsConfigWrapper.class.getClassLoader());
            LOGGER.debug("Detected ModernUI");
            isMuiLoaded = true;
        } catch (ClassNotFoundException e) {
            LOGGER.debug("ModernUI not found - ignored");
            isMuiLoaded = false;
        }
    }

    @Override
    public String getRefMapperConfig() {return null;}

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return isMuiLoaded;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

    @Override
    public List<String> getMixins() {return null;}

    /**
     * Called immediately <b>before</b> a mixin is applied to a target class,
     * allows any pre-application transformations to be applied.
     *
     * @param targetClassName Transformed name of the target class
     * @param targetClass     Target class tree
     * @param mixinClassName  Name of the mixin class
     * @param mixinInfo       Information about this mixin
     */
    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        LOGGER.debug("Detected targetClassName: {}", targetClassName);
        if ("icyllis.modernui.forge.ModernUIForge$Client".equals(targetClassName)) {
            new LoadFontsConfigASM(targetClass).run();
        }
    }

    /**
     * Called immediately <b>after</b> a mixin is applied to a target class,
     * allows any post-application transformations to be applied.
     *
     * @param targetClassName Transformed name of the target class
     * @param targetClass     Target class tree
     * @param mixinClassName  Name of the mixin class
     * @param mixinInfo       Information about this mixin
     */
    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
