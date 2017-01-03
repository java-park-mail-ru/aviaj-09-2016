package ru.aviaj.mechanics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aviaj.mechanics.baseobject.PlanePosition;
import ru.aviaj.mechanics.baseobject.Ring;
import ru.aviaj.mechanics.baseobject.TrackMap;
import ru.aviaj.mechanics.basetype.CollisionStatus;
import ru.aviaj.mechanics.basetype.Dot;
import ru.aviaj.mechanics.basetype.DotDouble;
import ru.aviaj.mechanics.gamesession.GameSession;
import ru.aviaj.mechanics.gamesession.GameSessionService;
import ru.aviaj.mechanics.geometry.Geometry;

import java.util.List;

@SuppressWarnings({"unused", "OverlyComplexBooleanExpression", "OverlyComplexMethod"})
@Service
public class MovementService {

    GameSessionService gameSessionService;

    @Autowired
    public MovementService(GameSessionService gameSessionService) {
        this.gameSessionService = gameSessionService;
    }

    private long processRingOverfly(PlanePosition position, Ring ring, int time) {

        final DotDouble projection = Geometry.dotPlaneProjection(position.getCenter(), ring.getNormal());

        final Dot nextPosition = new Dot(position.getCenter().getX() + position.getSpeedDirection().getX()*time,
                position.getCenter().getY() + position.getSpeedDirection().getY()*time,
                position.getCenter().getZ() + position.getSpeedDirection().getZ()*time);

        if ( (((float)position.getCenter().getZ() >= projection.getZ()) && ((float)nextPosition.getZ() < projection.getZ()))
                || (((float)position.getCenter().getZ() <= projection.getZ()) && ((float)nextPosition.getZ() > projection.getZ())))
        {
            final double distance = Geometry.dotDistance(projection, new DotDouble(ring.getCenter()));
            if (distance < (double)ring.getRadius()) {
                return ring.getRatingValue();
            }
        }

        return 0;
    }

    public long processRings(PlanePosition planePosition, List<Ring> rings, int time) {
        long result = 0;
        for(Ring ring : rings) {
            result += processRingOverfly(planePosition, ring, time);
        }

        return result;
    }

    public CollisionStatus processCollisions(GameSession gameSession, int time) {

        final TrackMap track = gameSession.getTrack();

        final PlanePosition planePositionFirst = gameSession.getPlayerFirst().getPlanePosition();

        final PlanePosition planePositionSecond = gameSession.getPlayerSecond().getPlanePosition();

        if ((planePositionFirst.getCenter().getX() > GameConfig.TRACK_WIDTH) ||
                (planePositionFirst.getCenter().getY() > GameConfig.TRACK_WIDTH)) {
            return CollisionStatus.OUT_OF_TRACK;
        }
        if ((planePositionSecond.getCenter().getX() > GameConfig.TRACK_WIDTH) ||
                (planePositionSecond.getCenter().getY() > GameConfig.TRACK_WIDTH)) {
            return CollisionStatus.OUT_OF_TRACK;
        }

        if (planePositionFirst.getCenter().getZ() < 5) {
            return CollisionStatus.GROUND;
        }
        if (planePositionSecond.getCenter().getZ() < 5) {
            return CollisionStatus.GROUND;
        }

        return CollisionStatus.OK;
    }

    public PlanePosition processMovements(PlanePosition planePosition, int time) {

        return new PlanePosition(
                new Dot(planePosition.getCenter().getX() + planePosition.getSpeedDirection().getX()*time,
                        planePosition.getCenter().getY() + planePosition.getSpeedDirection().getY()*time,
                        planePosition.getCenter().getZ() + planePosition.getSpeedDirection().getZ()*time),
                planePosition.getSpeedDirection()
        );

    }



}
