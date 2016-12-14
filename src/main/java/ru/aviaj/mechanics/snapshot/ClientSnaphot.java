package ru.aviaj.mechanics.snapshot;

import ru.aviaj.mechanics.basetype.Vector;

@SuppressWarnings("unused")
public class ClientSnaphot {

    private Vector speed;
    private long frameTime;

    public Vector getSpeed() {
        return speed;
    }

    public void setSpeed(Vector speed) {
        this.speed = speed;
    }

    public void setFrameTime(long frameTime) {
        this.frameTime = frameTime;
    }

    public long getFrameTime() {
        return frameTime;
    }
}
