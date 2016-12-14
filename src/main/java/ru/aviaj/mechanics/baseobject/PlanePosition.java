package ru.aviaj.mechanics.baseobject;

import ru.aviaj.mechanics.basetype.Dot;
import ru.aviaj.mechanics.basetype.Vector;

@SuppressWarnings("unused")
public class PlanePosition {

    private Dot center;
    private Vector direction;

    public PlanePosition(Dot center, Vector direction) {
        this.center = center;
        this.direction = direction;
    }

    public PlanePosition(long xC, long yC, long zC, long xD, long yD, long zD) {
        this.center = new Dot(xC, yC, zC);
        this.direction = new Vector(xD, yD, zD);
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
}
