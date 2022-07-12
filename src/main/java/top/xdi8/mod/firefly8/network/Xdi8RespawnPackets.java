package top.xdi8.mod.firefly8.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import top.xdi8.mod.firefly8.ext.IPlayerWithHiddenInventory;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Xdi8RespawnPackets {
    private static final ResourceLocation ID_RESPAWN =
            new ResourceLocation("firefly8", "respawn_from_xdi8");

    private static final String VER_MAJOR = "."/*0.*/, VER_MINOR = "0712";
    private static final Predicate<String> VER_VALID = s -> s.startsWith(VER_MAJOR);

    private static final SimpleChannel RESPAWN = NetworkRegistry.newSimpleChannel(
            ID_RESPAWN, () -> VER_MAJOR + VER_MINOR,
            VER_VALID, VER_VALID
    );

    public static void init() {
        RESPAWN.registerMessage(1000, RespawnHandler.class,
                (r, b) -> {}, b->RespawnHandler.INSTANCE,
                RespawnHandler::apply, Optional.of(NetworkDirection.PLAY_TO_SERVER));
    }

    public static final class RespawnHandler {
        static final RespawnHandler INSTANCE = new RespawnHandler();
        RespawnHandler() {}
        public void apply(Supplier<NetworkEvent.Context> ctxSup) {
            final ServerPlayer sender = Objects.requireNonNull(ctxSup.get().getSender(), "sender");
            final IPlayerWithHiddenInventory extPlayer = IPlayerWithHiddenInventory.xdi8$extend(sender);
        }
    }
}
