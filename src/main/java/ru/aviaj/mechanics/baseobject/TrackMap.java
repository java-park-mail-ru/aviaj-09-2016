package ru.aviaj.mechanics.baseobject;

import ru.aviaj.mechanics.GameConfig;
import ru.aviaj.mechanics.basetype.Dot;
import ru.aviaj.mechanics.basetype.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("unused")
public class TrackMap {

    private List<Ring> rings = new ArrayList<>();

    public void addRing(Ring ring) {
        rings.add(ring);
    }

    public List<Ring> getRings() {
        return rings;
    }

    public void randomize() {
        final Random random = new Random();

        final int ringCount = random.nextInt(2*GameConfig.DEFAULT_RINGS) + GameConfig.DEFAULT_RINGS;

        for (int i = 0; i < ringCount; i++)
        {
            final Dot randomDot = new Dot(
                    random.nextInt(GameConfig.TRACK_WIDTH - 150) + 100,
                    random.nextInt(GameConfig.TRACK_WIDTH - 150) + 100,
                    random.nextInt(GameConfig.TRACK_WIDTH - 150) + 100
            );

            final Vector randomDirection = new Vector(random.nextInt(10), random.nextInt(10), random.nextInt(10));

            addRing(new Ring(randomDot, randomDirection, GameConfig.DEFAULT_RADIUS,
                    random.nextInt(15) + GameConfig.MIN_RING_RATING_VALUE, i + 1));
        }

    }
}
