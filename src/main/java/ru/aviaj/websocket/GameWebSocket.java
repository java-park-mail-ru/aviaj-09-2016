package ru.aviaj.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.aviaj.database.exception.DbException;
import ru.aviaj.service.AccountService;
import ru.aviaj.service.SessionService;

public class GameWebSocket extends TextWebSocketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameWebSocket.class);

    private AccountService accountService;
    private SessionService sessionService;
    private ClientService clientService;


    @Autowired
    public GameWebSocket(AccountService accountService,
                         SessionService sessionService,
                         ClientService clientService) {
        this.accountService = accountService;
        this.sessionService = sessionService;
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
        //TODO: ping
    }
}
