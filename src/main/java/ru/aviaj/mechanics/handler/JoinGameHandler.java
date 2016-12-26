package ru.aviaj.mechanics.handler;

import org.springframework.beans.factory.annotation.Autowired;
import ru.aviaj.mechanics.Mechanics;
import ru.aviaj.mechanics.request.JoinRequest;
import ru.aviaj.websocket.ClientMessageHandler;
import ru.aviaj.websocket.ClientMessageHandlerService;
import ru.aviaj.websocket.HandleException;

import javax.annotation.PostConstruct;

@SuppressWarnings("unused")
public class JoinGameHandler extends ClientMessageHandler<JoinRequest.Request> {

    private Mechanics mechanics;
    private ClientMessageHandlerService messageHandlerService;

    @Autowired
    public JoinGameHandler(Mechanics mechanics, ClientMessageHandlerService messageHandlerService) {
        super(JoinRequest.Request.class);
        this.mechanics = mechanics;
        this.messageHandlerService = messageHandlerService;
    }

    @PostConstruct
    private void init() { messageHandlerService.registerHandler(JoinRequest.Request.class, this);}

    @Override
    public void handle(JoinRequest.Request message, long userId) throws HandleException {
        mechanics.addUser(userId);
    }
}
