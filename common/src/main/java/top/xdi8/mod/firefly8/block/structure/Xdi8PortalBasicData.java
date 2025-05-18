package top.xdi8.mod.firefly8.block.structure;

import io.github.qwerty770.mcmod.xdi8.registries.ResourceLocationTool;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.io.Reader;
import java.util.*;
import java.util.function.Predicate;

public class Xdi8PortalBasicData {
    private final Map<Vec3i, Predicate<BlockState>> map;

    Xdi8PortalBasicData(Map<Vec3i, Predicate<BlockState>> map) {
        this.map = map;
    }

    private static Predicate<BlockState> fromId(String id) {
        if (id.startsWith("#")) {
        TagKey<Block> tagKey = TagKey.create(Registries.BLOCK,
                    ResourceLocationTool.create(id.substring(1)));
            return blockState -> blockState.is(tagKey);
        }
        Optional<Holder.Reference<Block>> block = BuiltInRegistries.BLOCK.get(ResourceLocationTool.create(id));
        if (block.isEmpty()) throw new NullPointerException("Block " + id + " is invalid");
        return blockState -> blockState.is(block.get().value());
    }

    public boolean fits(BlockGetter level, BlockPos pos) {
        for (Map.Entry<Vec3i, Predicate<BlockState>> entry : map.entrySet()) {
            Vec3i vec3i = entry.getKey();
            Predicate<BlockState> blocks = entry.getValue();
            if (!blocks.test(level.getBlockState(pos.offset(vec3i))))
                return false;
        }
        return true;
    }

    public static @NotNull Xdi8PortalBasicData readText(Reader reader) {
        try {
            Scanner scanner = new Scanner(reader);
            Map<Vec3i, Predicate<BlockState>> m = new HashMap<>();
            while (scanner.hasNext()) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                int z = scanner.nextInt();
                String s = scanner.next();
                m.put(new Vec3i(x, y, z), fromId(s));
            }
            return new Xdi8PortalBasicData(m);
        } catch (Throwable t) {
            throw new IllegalArgumentException("Cannot read xdi8 portal basic data", t);
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
