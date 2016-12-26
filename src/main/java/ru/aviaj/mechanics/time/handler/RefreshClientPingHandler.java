package ru.aviaj.mechanics.time.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.aviaj.mechanics.Mechanics;
import ru.aviaj.mechanics.snapshot.ClientSnaphot;
import ru.aviaj.mechanics.time.PingService;
import ru.aviaj.mechanics.time.Timing;
import ru.aviaj.mechanics.time.request.ClientPingData;
import ru.aviaj.mechanics.time.request.GetClientPing;
import ru.aviaj.mechanics.time.request.RefreshClientPing;
import ru.aviaj.websocket.*;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class RefreshClientPingHandler extends ClientMessageHandler<RefreshClientPing.Request> {

    private ClientMessageHandlerService messageHandlerService;
    private PingService pingService;

    @Autowired
    public RefreshClientPingHandler(ClientMessageHandlerService messageHandlerService,
                                 PingService pingService) {
        super(RefreshClientPing.Request.class);
        this.messageHandlerService = messageHandlerService;
        this.pingService = pingService;
    }

    @PostConstruct
    private void init() { messageHandlerService.registerHandler(RefreshClientPing.Request.class, this);}

    @Override
    public void handle(RefreshClientPing.Request message, long userId) throws HandleException {
        pingService.refreshPing(userId);
    }

}
