package ru.aviaj.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.imageio.plugins.wbmp.WBMPImageReader;
import org.eclipse.jetty.websocket.api.WebSocketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import ru.aviaj.model.UserProfile;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

    private Map<Long, WebSocketSession> wsSessions = new ConcurrentHashMap<>();
    private ObjectMapper objectMapper = new ObjectMapper();

    private void addClient(long userId, WebSocketSession session) {
        wsSessions.put(userId, session);
    }

    public boolean isClientConnected(long userId) {
        return (wsSessions.containsKey(userId) && wsSessions.get(userId).isOpen());
    }

    public void removeClient(long userId) {
        wsSessions.remove(userId);
    }

    public void closeSession(long userId, CloseStatus closeStatus) {
        final WebSocketSession session = wsSessions.get(userId);

        if ((session != null) && (session.isOpen())) {
            try {
                session.close(closeStatus);
            } catch (IOException e) {
                LOGGER.error("Unable to close websocket session!", e);
            }
        }
    }

    public void sendClientMessage(long userId, ClientMessage message) throws IOException {
        final WebSocketSession session = wsSessions.get(userId);
        if (session == null) {
            throw new IOException("Unable to find session for id " + Long.toString(userId) + "!");
        }
        if (!session.isOpen()) {
            throw new IOException("Session is closed!");
        }

        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (JsonProcessingException | WebSocketException e) {
            throw new IOException("Unable to send message to client with id " + Long.toString(userId) + "!");
        }
    }


}
