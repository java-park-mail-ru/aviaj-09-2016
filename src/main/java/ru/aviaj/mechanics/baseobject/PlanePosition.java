package ru.aviaj.mechanics.baseobject;

import ru.aviaj.mechanics.GameConfig;
import ru.aviaj.mechanics.basetype.Dot;
import ru.aviaj.mechanics.basetype.Vector;

@SuppressWarnings("unused")
public class PlanePosition {

    private Dot center;
    private Vector speedDirection;

    private long planeWidth;
    private long planeHeight;
    private long planeLength;

    public PlanePosition() {
        this.center = new Dot();
        this.speedDirection = new Vector();

        this.planeHeight = GameConfig.PLANE_HEIGHT;
        this.planeLength = GameConfig.PLANE_LENGTH;
        this.planeWidth = GameConfig.TRACK_WIDTH;
    }

    public PlanePosition(Dot center, Vector speedDirection) {
        this.center = center;
        this.speedDirection = speedDirection;

        this.planeHeight = GameConfig.PLANE_HEIGHT;
        this.planeLength = GameConfig.PLANE_LENGTH;
        this.planeWidth = GameConfig.TRACK_WIDTH;
    }

    public Dot getCenter() {
        return center;
    }

    public void setCenter(Dot center) {
        this.center = center;
    }

    public void setSpeedDirection(Vector speedDirection) {
        this.speedDirection = speedDirection;
    }

    public Vector getSpeedDirection() {
        return speedDirection;
    }

    public long getPlaneWidth() {
        return planeWidth;
    }

    public long getPlaneHeight() {
        return planeHeight;
    }

    public long getPlaneLength() {
        return planeLength;
    }
}
