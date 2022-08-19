package top.xdi8.mod.firefly8.block;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.featurehouse.mcmod.spm.platform.api.reg.PlatformRegister;

public class FireflyBlockTags {
    private static final PlatformRegister reg = PlatformRegister.of("firefly8");
    public static final TagKey<Block> FIREFLIES_CAN_RELEASE = reg.blockTag("fireflies_can_release");
    public static final TagKey<Block> PORTAL_CORE = reg.blockTag("portal/core");
    public static final TagKey<Block> CENTER_PILLAR = reg.blockTag("portal/center_pillar");
    public static final TagKey<Block> BACK_PORTAL_FIRE_PLACEABLE = reg.blockTag("back_portal_fire_placeable");

}
