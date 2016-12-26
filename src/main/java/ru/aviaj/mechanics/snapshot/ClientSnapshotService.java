package ru.aviaj.mechanics.snapshot;

import org.springframework.stereotype.Service;
import ru.aviaj.mechanics.MovementService;
import ru.aviaj.mechanics.baseobject.Player;
import ru.aviaj.mechanics.baseobject.Ring;
import ru.aviaj.mechanics.basetype.CollisionStatus;
import ru.aviaj.mechanics.gamesession.GameSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ClientSnapshotService {

    private Map<Long, ArrayList<ClientSnaphot>> snapshots = new ConcurrentHashMap<>();

    private final MovementService movementService;

    public ClientSnapshotService(MovementService movementService) {
        this.movementService = movementService;
    }

    public void addSnapshot(long userId, ClientSnaphot clientSnaphot) {
        snapshots.putIfAbsent(userId, new ArrayList<>());
        snapshots.get(userId).add(clientSnaphot);
    }

    public List<ClientSnaphot> getSnapshot(long userId) {
        return snapshots.get(userId);
    }

    public CollisionStatus processSessionSnapshots(GameSession gameSession) {

        final List<Player> players = new ArrayList<>();
        players.add(gameSession.getPlayerFirst());
        players.add(gameSession.getPlayerSecond());

        final List<Ring> rings = gameSession.getTrack().getRings();

        for(Player player : players) {

            final List<ClientSnaphot> snaps = getSnapshot(player.getUserId());
            if (snaps.isEmpty()) {
                continue;
            }
            for (ClientSnaphot snaphot : snaps) {
                player.getPlanePosition().setSpeedDirection(snaphot.getSpeed());

                player.updateRating(movementService.processRings(player.getPlanePosition(),
                        rings, snaphot.getClientFrameTime()));

                final CollisionStatus collision = movementService.processCollisions(gameSession,
                        snaphot.getClientFrameTime());
                if (collision != CollisionStatus.OK) {
                    return collision;
                }

                player.setPlanePosition(movementService.processMovements(player.getPlanePosition(),
                        snaphot.getClientFrameTime()));
            }
        }

        return CollisionStatus.OK;
    }


    public void clearSnapshots() { snapshots.clear(); }
}
