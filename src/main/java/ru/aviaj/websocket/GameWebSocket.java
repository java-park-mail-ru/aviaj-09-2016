package ru.aviaj.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.aviaj.service.AccountService;
import ru.aviaj.service.SessionService;

public class GameWebSocket extends TextWebSocketHandler {

    private AccountService accountService;
    private SessionService sessionService;

    @Autowired
    public GameWebSocket(AccountService accountService,
                         SessionService sessionService) {
        this.accountService = accountService;
        this.sessionService = sessionService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) {
        
    }
}
