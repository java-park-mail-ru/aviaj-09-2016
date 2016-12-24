package ru.aviaj.mechanics.gamesession;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import ru.aviaj.mechanics.GameConfig;
import ru.aviaj.mechanics.baseobject.PlanePosition;
import ru.aviaj.mechanics.baseobject.Player;
import ru.aviaj.mechanics.basetype.Dot;
import ru.aviaj.mechanics.basetype.Vector;
import ru.aviaj.mechanics.request.InitRequest;
import ru.aviaj.mechanics.snapshot.ServerPlayerSnapshot;
import ru.aviaj.model.UserProfile;
import ru.aviaj.websocket.ClientMessage;
import ru.aviaj.websocket.ClientService;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.IOException;
import java.util.*;

public class GameSessionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameSessionService.class);


    private final Map<Long, GameSession> userGames  = new HashMap<>();
    private final Set<GameSession> gameSessions = new LinkedHashSet<>();

    private ClientService clientService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public GameSessionService(ClientService clientService) {
        this.clientService = clientService;
    }

    private void initGameSession(GameSession gameSession) {

        gameSession.getPlayerFirst().setPlanePosition(new PlanePosition(
                new Dot(0, 0, GameConfig.DEFAULT_HEIGHT),
                new Vector(GameConfig.DEFAULT_SPEED, 0, 0)
        ));
        gameSession.getPlayerSecond().setPlanePosition(new PlanePosition(
                new Dot(200, 200, GameConfig.DEFAULT_HEIGHT),
                new Vector(GameConfig.DEFAULT_SPEED, 0, 0)
        ));

        final List<Player> players = new ArrayList<>();
        players.add(gameSession.getPlayerFirst());
        players.add(gameSession.getPlayerSecond());

        final List<ServerPlayerSnapshot> playerSnapshots = new ArrayList<>();
        playerSnapshots.add(new ServerPlayerSnapshot(gameSession.getPlayerFirst()));
        playerSnapshots.add(new ServerPlayerSnapshot(gameSession.getPlayerSecond()));

        final Map<Long, String> names = new HashMap<>();
        names.put(gameSession.getPlayerFirst().getUserProfile().getId(),
                gameSession.getPlayerFirst().getUserProfile().getLogin());
        names.put(gameSession.getPlayerSecond().getUserProfile().getId(),
                gameSession.getPlayerSecond().getUserProfile().getLogin());


        for(Player player : players) {
            InitRequest.Request initRequest = new InitRequest.Request();
            initRequest.setUserId(player.getUserProfile().getId());
            initRequest.setPlayerNames(names);
            initRequest.setPlayers(playerSnapshots);

            try {
                ClientMessage clientMessage = new ClientMessage(InitRequest.class.getName(),
                        objectMapper.writeValueAsString(initRequest));
                clientService.sendClientMessage(player.getUserProfile().getId(), clientMessage);
            } catch (IOException e) {
                players.forEach(playerDisconnect -> clientService.closeSession(
                        playerDisconnect.getUserProfile().getId(), CloseStatus.SERVER_ERROR
                ));
                LOGGER.error("Unable to create message!", e);
            }

        }
    }

    public Set<GameSession> getGameSessions() {
        return gameSessions;
    }

    public GameSession getGamesession(long userId) {
        return userGames.get(userId);
    }

    public boolean isPlaying(long userId) {
        return userGames.containsKey(userId);
    }

    public GameSession startGame(UserProfile userFirst, UserProfile userSecond) {
        final GameSession gameSession = new GameSession(userFirst, userSecond);
        gameSessions.add(gameSession);
        userGames.put(userFirst.getId(), gameSession);
        userGames.put(userSecond.getId(), gameSession);
        initGameSession(gameSession);

        return gameSession;
    }

    public void GameOver(GameSession gameSession) {
        final boolean deleted = gameSessions.remove(gameSession);
        userGames.remove(gameSession.getPlayerFirst().getUserId());
        userGames.remove(gameSession.getPlayerSecond().getUserId());
        if (deleted) {
            clientService.closeSession(gameSession.getPlayerFirst().getUserId(), CloseStatus.NORMAL);
            clientService.closeSession(gameSession.getPlayerSecond().getUserId(), CloseStatus.NORMAL);
        }
    }
}