package ru.aviaj.mechanics.gamesession;

import ru.aviaj.mechanics.baseobject.Player;
import ru.aviaj.mechanics.baseobject.TrackMap;
import ru.aviaj.model.UserProfile;

import java.util.concurrent.atomic.AtomicLong;

public class GameSession {

    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);

    private final long id;

    private final Player playerFirst;
    private final Player playerSecond;

    private final TrackMap track = new TrackMap();

    public GameSession(UserProfile playerFirst, UserProfile playerSecond) {
        id = ID_GENERATOR.getAndIncrement();
        this.playerFirst = new Player(playerFirst);
        this.playerSecond = new Player(playerSecond);
    }
}
