package top.xdi8.mod.firefly8.block;

import io.github.qwerty770.mcmod.xdi8.api.InternalRegistryLogWrapper;
import io.github.qwerty770.mcmod.xdi8.util.tag.TagContainer;
import net.minecraft.world.level.block.Block;

import static io.github.qwerty770.mcmod.xdi8.util.registries.RegistryHelper.blockTag;

public class FireflyBlockTags {
    public static final InternalRegistryLogWrapper LOG_WRAPPER = InternalRegistryLogWrapper.firefly8("block_tags");
    public static final TagContainer<Block> FIREFLIES_CAN_RELEASE = blockTag("fireflies_can_release");
    public static final TagContainer<Block> PORTAL_CORE = blockTag("portal/core");
    public static final TagContainer<Block> CENTER_PILLAR = blockTag("portal/center_pillar");
    public static final TagContainer<Block> BACK_PORTAL_FIRE_PLACEABLE = blockTag("back_portal_fire_placeable");
}
