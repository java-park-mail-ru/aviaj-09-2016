package ru.aviaj.mechanics.geometry;

import ru.aviaj.mechanics.basetype.Dot;
import ru.aviaj.mechanics.basetype.Vector;

public final class Geometry {

    //Координаты проекции точки на плоскость
    public static Dot dotPlaneProjection(Dot dot, Vector planeNormal) {
        Determiner determiner = new Determiner();

        determiner.setValue(0, 0, planeNormal.getX());
        determiner.setValue(1, 0, );
        determiner.setValue(2, 0, );
        determiner.setValue(0, 1, );
        determiner.setValue(1, 1, );
        determiner.setValue(2, 1, );
        determiner.setValue(0, 2, );
        determiner.setValue(1, 2, );
        determiner.setValue(2, 2, );
    }
}
