package ru.aviaj.mechanics.baseobject;

import ru.aviaj.mechanics.GameConfig;
import ru.aviaj.mechanics.basetype.Dot;
import ru.aviaj.mechanics.basetype.Vector;

@SuppressWarnings("unused")
public class PlanePosition {

    private Dot center;
    private Vector direction;

    private long planeWidth;
    private long planeHeight;
    private long planeLength;

    public PlanePosition() {
        this.center = new Dot();
        this.direction = new Vector();

        this.planeHeight = GameConfig.PLANE_HEIGHT;
        this.planeLength = GameConfig.PLANE_LENGTH;
        this.planeWidth = GameConfig.TRACK_WIDTH;
    }

    public PlanePosition(Dot center, Vector direction) {
        this.center = center;
        this.direction = direction;

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

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    public Vector getDirection() {
        return direction;
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
