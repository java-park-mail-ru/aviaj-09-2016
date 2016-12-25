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

@SuppressWarnings({"unused", "OverlyComplexBooleanExpression", "OverlyComplexMethod"})
@Service
public class MovementService {

    GameSessionService gameSessionService;

    @Autowired
    public MovementService(GameSessionService gameSessionService) {
        this.gameSessionService = gameSessionService;
    }

    private CollisionStatus processRingCollision(PlanePosition position, Ring ring) {
        final DotDouble projection = Geometry.dotPlaneProjection(position.getCenter(), ring.getNormal());

        final Dot nextPosition = new Dot(position.getCenter().getX() + position.getSpeedDirection().getX(),
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

    private CollisionStatus processCollisions(GameSession gameSession) {

        final TrackMap track = gameSession.getTrack();

        final PlanePosition planePositionFirst = gameSession.getPlayerFirst().getPlanePosition();

        for(Ring ring : track.getRings()) {
            if (processRingCollision(planePositionFirst, ring) == CollisionStatus.RING_OVERFLY) {
                gameSession.updateRatingFirst(ring.getRatingValue());
            }
        }

        final PlanePosition planePositionSecond = gameSession.getPlayerSecond().getPlanePosition();

        for(Ring ring : track.getRings()) {
            if (processRingCollision(planePositionSecond, ring) == CollisionStatus.RING_OVERFLY) {
                gameSession.updateRatingSecond(ring.getRatingValue());
            }
        }

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

    public void processMovements(GameSession gameSession) {

        final PlanePosition planePositionFirst = gameSession.getPlayerFirst().getPlanePosition();
        final PlanePosition newFirstPosition = new PlanePosition(
                new Dot(planePositionFirst.getCenter().getX() + planePositionFirst.getSpeedDirection().getX(),
                        planePositionFirst.getCenter().getY() + planePositionFirst.getSpeedDirection().getY(),
                        planePositionFirst.getCenter().getZ() + planePositionFirst.getSpeedDirection().getZ()),
                planePositionFirst.getSpeedDirection()
        );
        gameSession.getPlayerFirst().setPlanePosition(newFirstPosition);

        final PlanePosition planePositionSecond = gameSession.getPlayerSecond().getPlanePosition();
        final PlanePosition newSecondPosition = new PlanePosition(
                new Dot(planePositionSecond.getCenter().getX() + planePositionSecond.getSpeedDirection().getX(),
                        planePositionSecond.getCenter().getY() + planePositionSecond.getSpeedDirection().getY(),
                        planePositionSecond.getCenter().getZ() + planePositionSecond.getSpeedDirection().getZ()),
                planePositionSecond.getSpeedDirection()
        );
        gameSession.getPlayerSecond().setPlanePosition(newFirstPosition);
    }

}
