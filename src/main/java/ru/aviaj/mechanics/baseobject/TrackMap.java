package ru.aviaj.mechanics.baseobject;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class TrackMap {

    private List<Ring> rings = new ArrayList<>();

    public void addRing(Ring ring) {
        rings.add(ring);
    }

    public List<Ring> getRings() {
        return rings;
    }
}
