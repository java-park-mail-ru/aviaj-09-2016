package ru.aviaj.mechanics.snapshot;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketMessage;
import ru.aviaj.mechanics.baseobject.Player;
import ru.aviaj.mechanics.gamesession.GameSession;
import ru.aviaj.websocket.ClientMessage;
import ru.aviaj.websocket.ClientService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class ServerSnapshotService {

    private ClientService clientService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public ServerSnapshotService(ClientService clientService) {
        this.clientService = clientService;
    }

    public void sendSnapshot(GameSession gameSession, long frameTime) {
        final List<Player> players = new ArrayList<>();
        players.add(gameSession.getPlayerFirst());
        players.add(gameSession.getPlayerSecond());

        final List<ServerPlayerSnapshot> serverPlayerSnapshots = new ArrayList<>();
        for (Player player : players) {
            serverPlayerSnapshots.add(new ServerPlayerSnapshot(player));
        }

        final ServerSnapshot serverSnapshot = new ServerSnapshot();
        serverSnapshot.setPlayers(serverPlayerSnapshots);
        serverSnapshot.setServerFrameTime(frameTime);

        try {
            final ClientMessage clientMessage = new ClientMessage(ServerSnapshot.class.getName(),
                    objectMapper.writeValueAsString(serverSnapshot));

            for (Player player : players) {
                clientService.sendClientMessage(player.getUserId(), clientMessage);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to send server snapshot!", e);
        }
    }
}
