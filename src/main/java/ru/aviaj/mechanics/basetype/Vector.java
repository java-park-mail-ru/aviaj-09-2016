package ru.aviaj.mechanics.basetype;

@SuppressWarnings({"unused", "InstanceVariableNamingConvention"})
public class Vector {

    private int x;
    private int y;
    private int z;

    public Vector() { x = y = z = 0; }

    public Vector(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public long getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
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
