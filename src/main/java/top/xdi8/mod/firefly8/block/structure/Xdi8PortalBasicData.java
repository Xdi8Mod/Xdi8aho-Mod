package top.xdi8.mod.firefly8.block.structure;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import org.featurehouse.mcmod.spm.util.tag.TagLike;
import org.jetbrains.annotations.NotNull;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Xdi8PortalBasicData {
    private final Map<Vec3i, TagLike<Block>> map;

    Xdi8PortalBasicData(Map<Vec3i, TagLike<Block>> map) {
        this.map = map;
    }

    private static TagLike<Block> fromId(String id) {
        if (id.startsWith("#")) {
            TagKey<Block> tagKey = BlockTags.create(new ResourceLocation(id.substring(1)));
            return TagLike.asTag(new ResourceLocation("block"), tagKey);
        }
        var block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(id));
        return TagLike.asItem(block);
    }

    public boolean fits(BlockGetter level, BlockPos pos) {
        for (Map.Entry<Vec3i, TagLike<Block>> entry : map.entrySet()) {
            Vec3i vec3i = entry.getKey();
            TagLike<Block> blocks = entry.getValue();
            if (!blocks.contains(level.getBlockState(pos.offset(vec3i)).getBlock()))
                return false;
        }
        return true;
    }

    public static @NotNull Xdi8PortalBasicData readText(Reader reader) {
        try {
            Scanner scanner = new Scanner(reader);
            Map<Vec3i, TagLike<Block>> m = new HashMap<>();
            while (scanner.hasNext()) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                int z = scanner.nextInt();
                String s = scanner.next();
                m.put(new Vec3i(x, y, z), fromId(s));
            }
            return new Xdi8PortalBasicData(m);
        } catch (Throwable t) {
            throw new RuntimeException("Cannot read xdi8 portal basic data", t);
        }
    }

    private static Xdi8PortalBasicData INSTANCE;
    public static @NotNull Xdi8PortalBasicData getInstance() {
        if (INSTANCE == null)
            throw new IllegalStateException("Access Xdi8PortalBasicData.getInstance() too early!");
        return INSTANCE;
    }

    static void setInstance(Xdi8PortalBasicData instance) {
        INSTANCE = instance;
    }
}
