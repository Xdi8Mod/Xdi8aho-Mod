package org.featurehouse.mcmod.spm.platform.api.event;

import net.minecraftforge.eventbus.api.Event;

abstract class InternalEvent extends Event {
    @Override
    public boolean isCancelable() {
        return super.isCancelable();
    }

    @Override
    public void setCanceled(boolean cancel) {
        super.setCanceled(cancel);
    }

    @Override
    public boolean isCanceled() {
        return super.isCanceled();
    }
}
