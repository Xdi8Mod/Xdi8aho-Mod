package top.xdi8.mod.firefly8.block;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class FireflyBlockTags {
    public static final TagKey<Block> FIREFLIES_CAN_RELEASE = create("fireflies_can_release");
    public static final TagKey<Block> PORTAL_CORE = create("portal/core");
    public static final TagKey<Block> CENTER_PILLAR = create("portal/center_pillar");
    public static final TagKey<Block> PORTAL_REPLACEABLE = create("portal/replaceable");
    public static final TagKey<Block> BACK_PORTAL_FIRE_PLACEABLE = create("back_portal_fire_placeable");

    private static TagKey<Block> create(String id) {
        return BlockTags.create(new ResourceLocation("firefly8", id));
    }
}
