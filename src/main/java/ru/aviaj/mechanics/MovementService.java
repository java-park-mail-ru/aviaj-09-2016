package ru.aviaj.mechanics;

import ru.aviaj.mechanics.baseobject.PlanePosition;
import ru.aviaj.mechanics.baseobject.Ring;
import ru.aviaj.mechanics.baseobject.TrackMap;
import ru.aviaj.mechanics.basetype.CollisionStatus;
import ru.aviaj.mechanics.gamesession.GameSession;

import java.util.HashSet;
import java.util.Set;

public class MovementService {

    final Set<GameSession> gameSessions = new HashSet<>();

    private CollisionStatus processRingCollision(PlanePosition position, Ring ring) {
        
    }

    public void addSession(GameSession gameSession) {
        gameSessions.add(gameSession);
    }

    public void processMovements() {
        for(GameSession gameSession : gameSessions) {

            TrackMap track = gameSession.getTrack();

        }
    }
}
