package org.featurehouse.mcmod.spm.platform.api.network;

import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Consumer;

/**
 * Should have constructors with {@link FriendlyByteBuf} and other essential
 * parameters.
 */
public interface PlayPacket extends Consumer<PlayNetworkEnvironment> {
    void toPacket(FriendlyByteBuf buf);

    @Override
    void accept(PlayNetworkEnvironment environment);
}
