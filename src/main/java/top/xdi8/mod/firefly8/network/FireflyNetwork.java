package top.xdi8.mod.firefly8.network;

import com.google.common.base.Suppliers;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import top.xdi8.mod.firefly8.ext.IServerPlayerWithHiddenInventory;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FireflyNetwork {
    static final Logger LOGGER = LogUtils.getLogger();
    private static final String VERSION = "B.A";
    private static final Predicate<String> ACCEPTED_VERSION = s ->
            s.startsWith("B.");

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("firefly8", "main"),
            Suppliers.ofInstance(VERSION),
            ACCEPTED_VERSION, ACCEPTED_VERSION
    );

    static {
        CHANNEL.registerMessage(0x2000, S2CDieIndeed.class, (a, b) -> {}, b -> S2CDieIndeed.getInstance(),
                S2CDieIndeed::processContext, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        CHANNEL.registerMessage(0x2010, S2CRespawn.class, (a, b) -> {}, b -> S2CRespawn.getInstance(),
                S2CRespawn::processContext, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        CHANNEL.registerMessage(0x2800, C2SRespawnConfirm.class, (a, b) -> {}, b -> C2SRespawnConfirm.getInstance(),
                C2SRespawnConfirm::processContext, Optional.of(NetworkDirection.PLAY_TO_SERVER));
    }

    public static void init() {}

    public static final class C2SRespawnConfirm {
        static final C2SRespawnConfirm INSTANCE = new C2SRespawnConfirm();
        public static C2SRespawnConfirm getInstance() { return INSTANCE; }

        void processContext(Supplier<NetworkEvent.Context> ctx) {
            final NetworkEvent.Context context = ctx.get();
            final @NotNull ServerPlayer sender = Objects.requireNonNull(context.getSender());
            context.enqueueWork(() -> {
                final ServerPlayer newPlayer =
                        sender.server.getPlayerList().respawn(sender, true);
                final IServerPlayerWithHiddenInventory newPlayerExt =
                        IServerPlayerWithHiddenInventory.xdi8$extend(newPlayer);
                newPlayerExt.xdi8$resetCooldown();
                newPlayerExt.xdi8$passPortalInv(IServerPlayerWithHiddenInventory.xdi8$extend(sender));
                sender.connection.player = newPlayer;
            });
        }

        private C2SRespawnConfirm() {}
    }

    public static final class S2CRespawn {
        static final S2CRespawn INSTANCE = new S2CRespawn();
        public static S2CRespawn getInstance() {return INSTANCE;}

        @OnlyIn(Dist.CLIENT)
        private void processContextImpl(NetworkEvent.Context ctx) {
            ctx.enqueueWork(() ->
                    CHANNEL.send(PacketDistributor.SERVER.noArg(), C2SRespawnConfirm.getInstance()));
        }

        void processContext(Supplier<NetworkEvent.Context> ctx) {
            try {
                processContextImpl(ctx.get());
            } catch (NoSuchMethodError e) {
                LOGGER.error("You're not running this on client!", e);
            }
        }

        private S2CRespawn() {}
    }

    public static final class S2CDieIndeed {
        @OnlyIn(Dist.CLIENT)
        private void processContextImpl(NetworkEvent.Context ctx) {
            ctx.enqueueWork(() -> {
                final Minecraft minecraft = Minecraft.getInstance();
                final LocalPlayer player = minecraft.player;
                if (player != null && minecraft.level != null) {
                    if (player.shouldShowDeathScreen()) {
                        minecraft.setScreen(null);
                    } else {
                        player.respawn();
                    }
                }
            });
            ctx.setPacketHandled(true);
        }

        void processContext(Supplier<NetworkEvent.Context> ctx) {
            try {
                processContextImpl(ctx.get());
            } catch (NoSuchMethodError e) {
                LOGGER.error("You're not running this on client!", e);
            }
        }

        static final S2CDieIndeed INSTANCE = new S2CDieIndeed();

        public static S2CDieIndeed getInstance() {return INSTANCE; }
        private S2CDieIndeed() {}
    }
}
