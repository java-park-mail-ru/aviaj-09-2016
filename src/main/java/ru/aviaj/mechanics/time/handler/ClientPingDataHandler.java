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
import ru.aviaj.websocket.*;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class ClientPingDataHandler extends ClientMessageHandler<ClientPingData.Response> {

    private ClientMessageHandlerService messageHandlerService;
    private PingService pingService;

    @Autowired
    public ClientPingDataHandler(ClientMessageHandlerService messageHandlerService,
                                PingService pingService) {
        super(ClientPingData.Response.class);
        this.messageHandlerService = messageHandlerService;
        this.pingService = pingService;
    }

    @PostConstruct
    private void init() { messageHandlerService.registerHandler(ClientPingData.Response.class, this);}

    @Override
    public void handle(ClientPingData.Response message, long userId) throws HandleException {
        pingService.rememberPing(userId, message.getTimeStamp(), message.getId());
    }

}
