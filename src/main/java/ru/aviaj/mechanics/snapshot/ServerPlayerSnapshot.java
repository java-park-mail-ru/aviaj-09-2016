package ru.aviaj.mechanics.snapshot;


import ru.aviaj.mechanics.baseobject.PlanePosition;

public class ServerPlayerSnapshot {

    private long userId;

    private PlanePosition playerPlanePosition;

    public long getUserId() {
        return userId;
    }

    public PlanePosition getPlayerPlanePosition() {
        return playerPlanePosition;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
