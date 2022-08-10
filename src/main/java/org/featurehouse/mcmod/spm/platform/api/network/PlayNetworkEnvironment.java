package org.featurehouse.mcmod.spm.platform.api.network;

import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public record PlayNetworkEnvironment(Optional<ServerPlayer> serverPlayer) {

}
