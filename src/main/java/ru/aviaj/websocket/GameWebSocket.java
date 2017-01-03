package ru.aviaj.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.aviaj.database.exception.DbException;
import ru.aviaj.mechanics.time.PingService;
import ru.aviaj.service.AccountService;
import ru.aviaj.service.SessionService;

import java.io.IOException;

@SuppressWarnings({"OverlyBroadCatchBlock", "FieldCanBeLocal", "unused"})
public class GameWebSocket extends TextWebSocketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameWebSocket.class);

    private AccountService accountService;
    private SessionService sessionService;
    private ClientService clientService;
    private PingService pingService;
    private ClientMessageHandlerService handlerService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public GameWebSocket(AccountService accountService,
                         SessionService sessionService,
                         ClientService clientService,
                         PingService pingService,
                         ClientMessageHandlerService handlerService) {
        this.accountService = accountService;
        this.sessionService = sessionService;
        this.clientService = clientService;
        this.pingService = pingService;
        this.handlerService = handlerService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) {
        final String session = webSocketSession.getAttributes().get("AVIAJSESSIONID").toString();
        long userId = 0;
        try {
            userId = sessionService.getUserIdBySession(session);
        } catch (DbException e) {
            LOGGER.error("Database error in SessionService!", e);
        }
        if (userId == 0)
            return;

        clientService.addClient(userId, webSocketSession);
        webSocketSession.getAttributes().put("AVIAJGAMEID", userId);
        pingService.refreshPing(userId);
    }

    @Override
    public void handleTextMessage(WebSocketSession webSocketSession, TextMessage textMessage) {
        final long userId = (long)webSocketSession.getAttributes().get("AVIAJGAMEID");
        if (userId == 0) {
            LOGGER.error("No user id in opened websocket session!");
            return;
        }

        final ClientMessage clientMessage;
        try {
            clientMessage = objectMapper.readValue(textMessage.getPayload(), ClientMessage.class);
        } catch (IOException e) {
            LOGGER.error("Error parsing JSON!", e);
            return;
        }

        try {
            handlerService.handle(clientMessage, userId);
        } catch (HandleException e) {
            LOGGER.error("Unable to handle message!", e);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) {
        LOGGER.error("Error in websocket transport!", throwable);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) {
        final String session = webSocketSession.getAttributes().toString();
        long userId = 0;
        try {
            userId = sessionService.getUserIdBySession(session);
        } catch (DbException e) {
            LOGGER.error("Database error in SessionService!", e);
        }
        if (userId == 0)
            return;

        clientService.removeClient(userId);
        webSocketSession.getAttributes().remove("AVIAJGAMEID");
    }

    @Override
    public boolean supportsPartialMessages() { return false; }
}
