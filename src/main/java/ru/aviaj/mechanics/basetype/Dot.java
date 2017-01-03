package ru.aviaj.mechanics.basetype;

@SuppressWarnings({"unused", "InstanceVariableNamingConvention", "ClassNamingConvention"})
public class Dot {

    private int x;
    private int y;
    private int z;

    public Dot() { x = y = z = 0 ; }

    public Dot(int x, int y, int z) {
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


}
