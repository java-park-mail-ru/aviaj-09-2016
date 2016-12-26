package ru.aviaj.mechanics.gamesession;

import org.jetbrains.annotations.Nullable;
import ru.aviaj.mechanics.baseobject.Player;
import ru.aviaj.mechanics.baseobject.TrackMap;
import ru.aviaj.model.UserProfile;

import java.util.concurrent.atomic.AtomicLong;

@SuppressWarnings("unused")
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
        if (userId == playerFirst.getUserProfile().getId()) {
            return playerFirst;
        }
        else if (userId == playerSecond.getUserProfile().getId()) {
            return playerSecond;
        }
        else
            return null;
    }
}
