package ru.aviaj.mechanics;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aviaj.database.exception.DbException;
import ru.aviaj.mechanics.basetype.CollisionStatus;
import ru.aviaj.mechanics.gamesession.GameSession;
import ru.aviaj.mechanics.gamesession.GameSessionService;
import ru.aviaj.mechanics.snapshot.ClientSnaphot;
import ru.aviaj.mechanics.snapshot.ClientSnapshotService;
import ru.aviaj.mechanics.snapshot.ServerSnapshotService;
import ru.aviaj.model.UserProfile;
import ru.aviaj.service.AccountService;
import ru.aviaj.websocket.ClientService;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@SuppressWarnings({"unused", "FieldCanBeLocal", "WhileLoopReplaceableByForEach"})
@Service
public class Mechanics implements IMechanics {


    private static final Logger LOGGER = LoggerFactory.getLogger(Mechanics.class);

    private AccountService accountService;
    private ClientService clientService;
    private ClientSnapshotService clientSnapshotService;
    private ServerSnapshotService serverSnapshotService;
    private MovementService movementService;
    private GameSessionService gameSessionService;

    private Set<Long> playingUsers = new HashSet<>();
    private ConcurrentLinkedQueue<Long> waitingUsers = new ConcurrentLinkedQueue<>();

    private final Queue<Runnable> tasks = new ConcurrentLinkedQueue<>();

    @Autowired
    public Mechanics(AccountService accountService,
                     ClientService clientService,
                     ServerSnapshotService serverSnapshotService,
                     MovementService movementService,
                     GameSessionService gameSessionService) {
        this.accountService = accountService;
        this.clientService = clientService;
        this.serverSnapshotService = serverSnapshotService;
        this.movementService = movementService;
        this.gameSessionService = gameSessionService;
        this.clientSnapshotService = new ClientSnapshotService(movementService);
    }

    @Override
    public void addClientSnaphot(long userId, ClientSnaphot clientSnaphot) {
        tasks.add(() -> clientSnapshotService.addSnapshot(userId, clientSnaphot));
    }

    @Override
    public void addUser(@NotNull long userId) {
        if (gameSessionService.isPlaying(userId)) {
            return;
        }
        waitingUsers.add(userId);
    }

    private void tryStartGames() {
        final Set<UserProfile> matchedPlayers = new HashSet<>();

        while ((waitingUsers.size() >= 2) || (waitingUsers.size() >= 1 && matchedPlayers.size() >= 1)) {
            final long candidateId = waitingUsers.poll();
            if (!clientService.isClientConnected(candidateId)) {
                continue;
            }
            try {
                matchedPlayers.add(accountService.getUserById(candidateId));
            } catch (DbException e) {
                LOGGER.error("Database error!", e);
                continue;
            }

            if (matchedPlayers.size() == 2) {
                final Iterator<UserProfile> it = matchedPlayers.iterator();
                gameSessionService.startGame(it.next(), it.next());
                matchedPlayers.clear();
            }

            matchedPlayers.stream().map(UserProfile::getId).forEach(waitingUsers::add);
        }

    }

    @Override
    public void gameStep(long frameTime) {
        while (!tasks.isEmpty()) {
            final Runnable task = tasks.poll();
            if (task != null) {
                try {
                    task.run();
                } catch (RuntimeException e) {
                    LOGGER.error("Unable to run task!", e);
                }
            }
        }

        final List<GameSession> deleteSessions = new ArrayList<>();

        for (GameSession gameSession : gameSessionService.getGameSessions()) {
            if (clientSnapshotService.processSessionSnapshots(gameSession) != CollisionStatus.OK) {
                deleteSessions.add(gameSession);
            }
        }

        final Iterator<GameSession> it = deleteSessions.iterator();
        while (it.hasNext()) {
            final GameSession gameSession = it.next();
            try {
                serverSnapshotService.sendSnapshot(gameSession, frameTime);
            } catch (RuntimeException e) {
                LOGGER.error("Unable to send snapshot with id " + Long.toString(gameSession.getId()) + '!', e);
                deleteSessions.add(gameSession);
            }
        }

        tryStartGames();
        clientSnapshotService.clearSnapshots();
    }

    @Override
    public void reset() {

    }
}
