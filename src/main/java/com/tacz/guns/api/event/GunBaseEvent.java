package com.tacz.guns.api.event;

public abstract class GunBaseEvent extends BaseEvent {
    public boolean post() {
        sendEvent();
        return isCanceled();
    }
}
