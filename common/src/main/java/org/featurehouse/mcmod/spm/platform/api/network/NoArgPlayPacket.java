package org.featurehouse.mcmod.spm.platform.api.network;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Play packets with no arguments. Should have a constructor
 * with ({@code Consumer<}{@link NetworkManager.PacketContext}{@code >}).
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
