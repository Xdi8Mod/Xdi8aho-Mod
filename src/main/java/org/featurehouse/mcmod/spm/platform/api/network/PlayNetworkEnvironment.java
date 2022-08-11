package org.featurehouse.mcmod.spm.platform.api.network;

import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;
import java.util.function.Consumer;

public record PlayNetworkEnvironment(Optional<ServerPlayer> serverPlayer,
                                     Consumer<Runnable> workQueue,
                                     Runnable doneMarker) {
    public void enqueueWork(Runnable runnable) {
        workQueue().accept(runnable);
    }

    public void markDone() {
        doneMarker().run();
    }

    @SuppressWarnings("all")
    @Deprecated // don't create by yourself, as the constructor may change
    public PlayNetworkEnvironment {}
}
