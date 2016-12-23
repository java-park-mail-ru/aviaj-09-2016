package ru.aviaj.mechanics.basetype;

@SuppressWarnings({"unused", "InstanceVariableNamingConvention"})
public class Vector {

    private long x;
    private long y;
    private long z;

    public Vector() { x = y = z = 0; }

    public Vector(long x, long y, long z) {
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

    public double getAbs() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    public long getAbsSquare() {
        return (x*x + y*y + z*z);
    }
}
