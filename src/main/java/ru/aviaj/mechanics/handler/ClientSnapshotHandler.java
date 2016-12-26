package ru.aviaj.mechanics.handler;

import ch.qos.logback.core.net.server.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.aviaj.mechanics.Mechanics;
import ru.aviaj.mechanics.snapshot.ClientSnaphot;
import ru.aviaj.websocket.ClientMessageHandler;
import ru.aviaj.websocket.ClientMessageHandlerService;
import ru.aviaj.websocket.HandleException;

import javax.annotation.PostConstruct;

@Component
public class ClientSnapshotHandler extends ClientMessageHandler<ClientSnaphot> {

    private Mechanics mechanics;
    private ClientMessageHandlerService messageHandlerService;

    @Autowired
    public ClientSnapshotHandler(Mechanics mechanics, ClientMessageHandlerService messageHandlerService) {
        super(ClientSnaphot.class);
        this.mechanics = mechanics;
        this.messageHandlerService = messageHandlerService;
    }

    @PostConstruct
    private void init() { messageHandlerService.registerHandler(ClientSnaphot.class, this);}

    @Override
    public void handle(ClientSnaphot message, long userId) throws HandleException {
        mechanics.addClientSnaphot(userId, message);
    }

}
