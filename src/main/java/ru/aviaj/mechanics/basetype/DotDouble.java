package ru.aviaj.mechanics.basetype;

@SuppressWarnings({"InstanceVariableNamingConvention", "unused"})
public class DotDouble {

    private double x;
    private double y;
    private double z;

    public DotDouble() { x = y = z = 0 ; }

    public DotDouble(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public DotDouble(Dot dot) {
        this.x = (double)dot.getX();
        this.y = (double)dot.getY();
        this.z = (double)dot.getZ();
    }

    public double getX() {
        return x;
    }

    public double getY() { return y; }

    public double getZ() { return z; }

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
