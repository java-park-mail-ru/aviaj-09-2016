package ru.aviaj.mechanics.snapshot;


import ru.aviaj.mechanics.baseobject.PlanePosition;
import ru.aviaj.mechanics.baseobject.Player;

@SuppressWarnings("unused")
public class ServerPlayerSnapshot {

    private long userId;

    private PlanePosition planePosition;

    public ServerPlayerSnapshot() { }

    public ServerPlayerSnapshot(long userId, PlanePosition planePosition) {
        this.userId = userId;
        this.planePosition = planePosition;
    }

    public ServerPlayerSnapshot(Player player) {
        this.userId = player.getUserId();
        this.planePosition = player.getPlanePosition();
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
