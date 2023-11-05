package top.xdi8.mod.firefly8.entity;

import it.unimi.dsi.fastutil.objects.Object2LongMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.UUID;

public final class FireflyEntityData {
    public static final long CHARGE_TIME = 24000L;  // 20min
    public static final long OWNER_TIME = 72000L;   // 1h

    public static void saveToTag(CompoundTag tag, FireflyEntity firefly) {
        tag.putBoolean("NoAi", firefly.isNoAi());
        tag.putBoolean("Invulnerable", firefly.isInvulnerable());
        tag.put("OwnerData", serializeOwners(firefly.getOwnerMap()));
    }

    public static void loadFromTag(FireflyEntity firefly, CompoundTag tag) {
        if (tag.contains("NoAi", Tag.TAG_BYTE)) firefly.setNoAi(tag.getBoolean("NoAi"));
        if (tag.contains("Invulnerable", Tag.TAG_BYTE)) firefly.setInvulnerable(tag.getBoolean("Invulnerable"));
        if (tag.contains("OwnerData", Tag.TAG_LIST))
            deserializeOwners(firefly.getOwnerMap(), tag.getList("OwnerData", Tag.TAG_COMPOUND));
    }

    static ListTag serializeOwners(Object2LongMap<UUID> ownerMap) {
        ListTag root = new ListTag();
        ownerMap.forEach((uuid, outOfBottleTime) -> {
            CompoundTag tag = new CompoundTag();
            root.add(tag);
            tag.putUUID("OwnerID", uuid);
            tag.putLong("ReleaseTime", outOfBottleTime);
        });
        return root;
    }

    static void deserializeOwners(Object2LongMap<UUID> ownerMap, ListTag root) {
        if (root.getElementType() == Tag.TAG_COMPOUND) {
            for (Tag t : root) {
                CompoundTag tag = (CompoundTag) t;
                UUID uuid = tag.getUUID("OwnerID");
                long releaseTime = tag.getLong("ReleaseTime");
                ownerMap.put(uuid, releaseTime);
            }
        }
    }

    static void deleteOutdatedOwners(Object2LongMap<UUID> ownerMap, long currentTime) {
        ownerMap.forEach((uuid, outOfBottleTime) -> {
            if (currentTime - outOfBottleTime > OWNER_TIME) {
                ownerMap.removeLong(uuid);
            }
        });
    }
}
