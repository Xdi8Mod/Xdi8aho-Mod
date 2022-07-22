package top.xdi8.mod.firefly8.world;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.Collection;
import java.util.Collections;

public class Xdi8DimensionUtils {
    static final Collection<ResourceLocation> SPECIAL_RESPAWN =
            Collections.singleton(new ResourceLocation("firefly8", "xdi8aho"));

    public static boolean canRedirectRespawn(Level level) {
        return SPECIAL_RESPAWN.contains(level.dimension().location());
    }
}
