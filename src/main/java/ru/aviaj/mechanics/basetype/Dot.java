package ru.aviaj.mechanics.basetype;

@SuppressWarnings({"unused", "InstanceVariableNamingConvention", "ClassNamingConvention"})
public class Dot {

    private long x;
    private long y;
    private long z;

    public Dot() { x = y = z = 0 ; }

    public Dot(long x, long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public long getX() {
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public void setY(long y) {
        this.y = y;
    }

    public void setZ(long z) {
        this.z = z;
    }

    public long getY() {
        return y;
    }

    public long getZ() {
        return z;
    }
}
