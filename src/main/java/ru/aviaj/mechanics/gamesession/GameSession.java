package ru.aviaj.mechanics.gamesession;

import org.jetbrains.annotations.Nullable;
import ru.aviaj.mechanics.baseobject.Player;
import ru.aviaj.mechanics.baseobject.TrackMap;

import java.util.concurrent.atomic.AtomicLong;

@SuppressWarnings("unused")
public class GameSession {

    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);

    private final long id;

    private final Player playerFirst;
    private final Player playerSecond;

    private final TrackMap track = new TrackMap();

    public GameSession(long idFirst, String loginFirst, long idSecond, String loginSecond) {
        id = ID_GENERATOR.getAndIncrement();
        this.playerFirst = new Player(idFirst, loginFirst);
        this.playerSecond = new Player(idFirst, loginFirst);
        track.randomize();
    }

    public void updateRatingFirst(long updateValue) {
        playerFirst.updateRating(updateValue);
    }

    public void updateRatingSecond(long updateValue) {
        playerSecond.updateRating(updateValue);
    }

    public long getId() {
        return id;
    }

    public Player getPlayerFirst() {
        return playerFirst;
    }

    public Player getPlayerSecond() {
        return playerSecond;
    }

    public TrackMap getTrack() {
        return track;
    }

    @Nullable
    public Player getPlayerForUser(long userId) {
        if (userId == playerFirst.getUserId()) {
            return playerFirst;
        }
        else if (userId == playerSecond.getUserId()) {
            return playerSecond;
        }
        else
            return null;
    }
}
