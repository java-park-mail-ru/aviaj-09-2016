package ru.aviaj.mechanics.snapshot;


import ru.aviaj.mechanics.baseobject.PlanePosition;

public class ServerPlayerSnapshot {

    private long userId;

    private PlanePosition planePosition;

    public ServerPlayerSnapshot() { }

    public ServerPlayerSnapshot(long userId, PlanePosition planePosition) {
        this.userId = userId;
        this.planePosition = planePosition;
    }

    public long getUserId() {
        return userId;
    }

    public PlanePosition getPlanePosition() {
        return planePosition;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setPlanePosition(PlanePosition planePosition) {
        this.planePosition = planePosition;
    }
}
