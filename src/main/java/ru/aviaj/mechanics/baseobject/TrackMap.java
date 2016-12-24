package ru.aviaj.mechanics.baseobject;

import ru.aviaj.mechanics.GameConfig;
import ru.aviaj.mechanics.basetype.Dot;
import ru.aviaj.mechanics.basetype.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("unused")
public class TrackMap {

    private int width = GameConfig.TRACK_WIDTH;

    private List<Ring> rings = new ArrayList<>();

    public void addRing(Ring ring) {
        rings.add(ring);
    }

    public List<Ring> getRings() {
        return rings;
    }

    public int getWidth() { return width; }

    public void randomize() {
        final Random random = new Random();

        final int ringCount = random.nextInt(2*GameConfig.DEFAULT_RINGS) + GameConfig.DEFAULT_RINGS;

        for (int i = 0; i < ringCount; i++)
        {
            boolean randomSign = random.nextBoolean();
            final int randomSignX = (randomSign ? 1 : -1);
            randomSign = random.nextBoolean();
            final int randomSignY = (randomSign ? 1 : -1);

            final Dot randomDot = new Dot(
                    (random.nextInt(GameConfig.TRACK_WIDTH - 150) + 100)*randomSignX,
                    (random.nextInt(GameConfig.TRACK_WIDTH - 150) + 100)*randomSignY,
                    random.nextInt(GameConfig.TRACK_WIDTH - 150) + 100
            );

            final Vector randomDirection = new Vector(random.nextInt(10), random.nextInt(10), random.nextInt(10));

            addRing(new Ring(randomDot, randomDirection, GameConfig.DEFAULT_RADIUS,
                    random.nextInt(15) + GameConfig.MIN_RING_RATING_VALUE, i + 1));
        }

    }
}
