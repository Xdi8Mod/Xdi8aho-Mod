package org.featurehouse.mcmod.spm.platform.api.network;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public sealed interface PlayChannel permits PlayChannelImpl {
    @CanIgnoreReturnValue
    NoArgPlayPacket registerC2S(int id, Consumer<NetworkManager.PacketContext> lambda);
    @CanIgnoreReturnValue
    NoArgPlayPacket registerS2C(int id, Consumer<NetworkManager.PacketContext> lambda);
    <M extends PlayPacket> void registerC2S(Class<M> type, Function<FriendlyByteBuf, M> decoder);
    <M extends PlayPacket> void registerS2C(Class<M> type, Function<FriendlyByteBuf, M> decoder);

    void sendC2S(PlayPacket packet);
    void sendS2CPlayer(PlayPacket packet, Supplier<ServerPlayer> playerSup);
    // no-arg only
    void sendC2S(int id) throws IllegalArgumentException;
    void sendS2CPlayer(int id, Supplier<ServerPlayer> playerSup) throws IllegalArgumentException;

    ResourceLocation id();

    static PlayChannel create(ResourceLocation id) {
        return new PlayChannelImpl(id);
    }
}
