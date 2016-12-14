package ru.aviaj.mechanics.snapshot;

@SuppressWarnings("unused")
public class ClientSnaphot {

    private long speedX;
    private long speedY;
    private long speedZ;

    private long frameTime;

    public long getSpeedX() {
        return speedX;
    }

    public long getSpeedY() {
        return speedY;
    }

    public void setSpeedX(long speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(long speedY) {
        this.speedY = speedY;
    }

    public void setSpeedZ(long speedZ) {
        this.speedZ = speedZ;
    }

    public void setFrameTime(long frameTime) {
        this.frameTime = frameTime;
    }

    public long getSpeedZ() {

        return speedZ;
    }

    public long getFrameTime() {
        return frameTime;
    }
}
