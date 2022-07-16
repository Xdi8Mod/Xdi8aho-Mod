package top.xdi8.mod.firefly8.block;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class FireflyBlockTags {
    public static final TagKey<Block> FIREFLIES_CAN_RELEASE = create("fireflies_can_release");

    private static TagKey<Block> create(String id) {
        return BlockTags.create(new ResourceLocation("firefly8", id));
    }
}
