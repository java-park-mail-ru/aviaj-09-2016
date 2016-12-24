package ru.aviaj.mechanics.baseobject;

import ru.aviaj.mechanics.basetype.Dot;
import ru.aviaj.mechanics.basetype.Vector;

@SuppressWarnings("unused")
public class Ring {
    private Dot center;
    private Vector normal;
    private int id;
    private long radius;
    private long ratingValue;

    public Ring(Dot center, Vector normal, long radius, long ratingValue, int id) {
        this.center = center;
        this.normal = normal;
        this.id = id;
        this.radius = radius;
        this.ratingValue = ratingValue;
    }

    public Dot getCenter() {
        return center;
    }

    public Vector getNormal() {
        return normal;
    }

    public int getId() {
        return id;
    }

    public void setCenter(Dot center) {
        this.center = center;
    }

    public void setNormal(Vector normal) {
        this.normal = normal;
    }

    public void setRadius(long radius) {
        this.radius = radius;
    }

    public long getRadius() {
        return radius;
    }

    public void setRatingValue(long ratingValue) {
        this.ratingValue = ratingValue;
    }

    public long getRatingValue() {
        return ratingValue;
    }

}
