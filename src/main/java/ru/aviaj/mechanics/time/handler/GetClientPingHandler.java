package ru.aviaj.mechanics.time.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.aviaj.mechanics.time.PingService;
import ru.aviaj.mechanics.time.Timing;
import ru.aviaj.mechanics.time.request.GetClientPing;
import ru.aviaj.websocket.*;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SuppressWarnings("unused")
@Component
public class GetClientPingHandler extends ClientMessageHandler<GetClientPing.Request> {

    private ClientMessageHandlerService messageHandlerService;
    private ClientService clientService;
    private PingService pingService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public GetClientPingHandler(ClientMessageHandlerService messageHandlerService,
                                ClientService clientService,
                                PingService pingService) {
        super(GetClientPing.Request.class);
        this.messageHandlerService = messageHandlerService;
        this.clientService = clientService;
        this.pingService = pingService;
    }

    @PostConstruct
    private void init() { messageHandlerService.registerHandler(GetClientPing.Request.class, this);}

    @Override
    public void handle(GetClientPing.Request message, long userId) throws HandleException {

        final Timing timing = pingService.getTiming(userId);
        final GetClientPing.Response.Builder builder = GetClientPing.Response.createBuilder();

        if (timing != null) {
            builder.ping(timing.getClientPing()).timeShift(timing.getClientTimeshift());
        }

        try {
            final ClientMessage clientMessage = new ClientMessage(GetClientPing.Response.class.getName(),
                    objectMapper.writeValueAsString(builder.build()));
            clientService.sendClientMessage(userId, clientMessage);
        } catch (JsonProcessingException e) {
            throw new HandleException("Unable to convert message to JSON for user with id " + Long.toString(userId), e);
        } catch (IOException e) {
            throw new HandleException("Unable to send message to user with id " + Long.toString(userId), e);
        }

    }

}
