package org.featurehouse.mcmod.spm.platform.api.network;

import net.minecraft.network.FriendlyByteBuf;

/**
 * Play packets with no arguments. Should have a constructor
 * with ({@code Consumer<}{@link PlayNetworkEnvironment}{@code >}).
 * <br>
 * This sealed interface may be implemented by ASM generators only.
 */
public sealed interface NoArgPlayPacket extends PlayPacket
        permits NoArgPlayPacketWrapper, AlternativePlayPacketWrapper {
    @Override
    default void toPacket(FriendlyByteBuf buf) {}
}

non-sealed interface NoArgPlayPacketWrapper extends NoArgPlayPacket {}
non-sealed interface AlternativePlayPacketWrapper extends NoArgPlayPacket {}
