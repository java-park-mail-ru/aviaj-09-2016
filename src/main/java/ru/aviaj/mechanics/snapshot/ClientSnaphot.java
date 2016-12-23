package ru.aviaj.mechanics.snapshot;

import ru.aviaj.mechanics.basetype.Vector;

@SuppressWarnings("unused")
public class ClientSnaphot {

    private Vector speed;
    private long clientFrameTime;

    public Vector getSpeed() {
        return speed;
    }

    public void setSpeed(Vector speed) {
        this.speed = speed;
    }

    public void setClientFrameTime(long clientFrameTime) {
        this.clientFrameTime = clientFrameTime;
    }

    public long getClientFrameTime() {
        return clientFrameTime;
    }
}
