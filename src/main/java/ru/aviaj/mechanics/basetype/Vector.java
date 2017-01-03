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

    public int getX() {
        return x;
    }

    public int getY() { return y; }

    public int getZ() { return z; }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }



    public double getAbs() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    public int getAbsSquare() {
        return (x*x + y*y + z*z);
    }
}
