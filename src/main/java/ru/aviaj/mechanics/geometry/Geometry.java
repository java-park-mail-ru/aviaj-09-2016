package ru.aviaj.mechanics.geometry;

import ru.aviaj.mechanics.basetype.Dot;
import ru.aviaj.mechanics.basetype.DotDouble;
import ru.aviaj.mechanics.basetype.Vector;

public final class Geometry {

    //Координаты проекции точки на плоскость
    public static DotDouble dotPlaneProjection(Dot dot, Vector planeNormal) {
        final Determiner determiner = new Determiner();

        determiner.setValue(0, 0, planeNormal.getY());
        determiner.setValue(1, 0, planeNormal.getZ());
        determiner.setValue(2, 0, planeNormal.getX());
        determiner.setValue(0, 1, -planeNormal.getX());
        determiner.setValue(1, 1, 0);
        determiner.setValue(2, 1, planeNormal.getY());
        determiner.setValue(0, 2, 0);
        determiner.setValue(1, 2, -planeNormal.getX());
        determiner.setValue(2, 2, planeNormal.getZ());

        final int det = determiner.count();

        determiner.setValue(0, 0, planeNormal.getY()*dot.getX() - planeNormal.getX()*dot.getY());
        determiner.setValue(1, 0, planeNormal.getZ()*dot.getX() - planeNormal.getX()*dot.getZ());
        determiner.setValue(2, 0, planeNormal.getX()*dot.getX() + planeNormal.getY()*dot.getY()
                + planeNormal.getZ()*dot.getZ());
        determiner.setValue(0, 1, -planeNormal.getX());
        determiner.setValue(1, 1, 0);
        determiner.setValue(2, 1, planeNormal.getY());
        determiner.setValue(0, 2, 0);
        determiner.setValue(1, 2, -planeNormal.getX());
        determiner.setValue(2, 2, planeNormal.getZ());

        final double detX = (double) determiner.count();

        determiner.setValue(0, 0, planeNormal.getY());
        determiner.setValue(1, 0, planeNormal.getZ());
        determiner.setValue(2, 0, planeNormal.getX());
        determiner.setValue(0, 1, planeNormal.getY()*dot.getX() - planeNormal.getX()*dot.getY());
        determiner.setValue(1, 1, planeNormal.getZ()*dot.getX() - planeNormal.getX()*dot.getZ());
        determiner.setValue(2, 1, planeNormal.getX()*dot.getX() + planeNormal.getY()*dot.getY()
                + planeNormal.getZ()*dot.getZ());
        determiner.setValue(0, 2, 0);
        determiner.setValue(1, 2, -planeNormal.getX());
        determiner.setValue(2, 2, planeNormal.getZ());

        final double detY = (double) determiner.count();

        determiner.setValue(0, 0, planeNormal.getY());
        determiner.setValue(1, 0, planeNormal.getZ());
        determiner.setValue(2, 0, planeNormal.getX());
        determiner.setValue(0, 1, -planeNormal.getX());
        determiner.setValue(1, 1, 0);
        determiner.setValue(2, 1, planeNormal.getY());
        determiner.setValue(0, 2, planeNormal.getY()*dot.getX() - planeNormal.getX()*dot.getY());
        determiner.setValue(1, 2, planeNormal.getZ()*dot.getX() - planeNormal.getX()*dot.getZ());
        determiner.setValue(2, 2, planeNormal.getX()*dot.getX() + planeNormal.getY()*dot.getY()
                + planeNormal.getZ()*dot.getZ());

        final double detZ = (double)determiner.count();

        return new DotDouble((detX/det), (detY/det), (detZ)/(det));
    }

    public static double dotDistance(DotDouble dotA, DotDouble dotB) {
        return Math.sqrt((dotB.getX() - dotA.getX())*(dotB.getX() - dotA.getX()) +
                (dotB.getY() - dotA.getY())*(dotB.getY() - dotA.getY()) +
                (dotB.getZ() - dotA.getZ())*(dotB.getZ() - dotA.getZ()));
    }
}
