package org.featurehouse.mcmod.spm.platform.api.event;

public abstract class BaseEvent extends InternalEvent {
    @Override
    public boolean isCanceled() {
        return super.isCanceled();
    }

    @Override
    public boolean isCancelable() {
        return super.isCancelable();
    }

    @Override
    public void setCanceled(boolean cancel) {
        super.setCanceled(cancel);
    }
}
