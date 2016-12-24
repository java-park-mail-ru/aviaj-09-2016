package ru.aviaj.mechanics;

import ru.aviaj.mechanics.baseobject.PlanePosition;
import ru.aviaj.mechanics.baseobject.Ring;
import ru.aviaj.mechanics.baseobject.TrackMap;
import ru.aviaj.mechanics.basetype.CollisionStatus;
import ru.aviaj.mechanics.basetype.Dot;
import ru.aviaj.mechanics.basetype.DotDouble;
import ru.aviaj.mechanics.gamesession.GameSession;
import ru.aviaj.mechanics.geometry.Geometry;

import java.util.HashSet;
import java.util.Set;

public class MovementService {

    final Set<GameSession> gameSessions = new HashSet<>();

    private CollisionStatus processRingCollision(PlanePosition position, Ring ring) {
        DotDouble projection = Geometry.dotPlaneProjection(position.getCenter(), ring.getNormal());

        Dot nextPosition = new Dot(position.getCenter().getX() + position.getSpeedDirection().getX(),
                position.getCenter().getY() + position.getSpeedDirection().getY(),
                position.getCenter().getZ() + position.getSpeedDirection().getZ());

        if ( (((float)position.getCenter().getZ() >= projection.getZ()) && ((float)nextPosition.getZ() < projection.getZ()))
                || (((float)position.getCenter().getZ() <= projection.getZ()) && ((float)nextPosition.getZ() > projection.getZ())))
        {
            final double distance = Geometry.dotDistance(projection, new DotDouble(ring.getCenter()));
            if (distance < (double)ring.getRadius()) {
                return CollisionStatus.RING_OVERFLY;
            }
        }

        return CollisionStatus.NONE;
    }

    public void addSession(GameSession gameSession) {
        gameSessions.add(gameSession);
    }

    public void processMovements() {
        for(GameSession gameSession : gameSessions) {

            TrackMap track = gameSession.getTrack();

            PlanePosition planePosition = gameSession.getPlayerFirst().getPlanePosition();

            for(Ring ring : track.getRings()) {
                if (processRingCollision(planePosition, ring) == CollisionStatus.RING_OVERFLY) {
                    gameSession.updateRatingFirst(ring.getRatingValue());
                }
            }


        }
    }

}
