package ru.aviaj.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.validation.constraints.NotNull;
import java.io.IOException;

public abstract class ClientMessageHandler<T> {

    private final Class<T> clazz;
    private ObjectMapper objectMapper = new ObjectMapper();

    public ClientMessageHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    public abstract void handle(T message, long userId) throws HandleException;

    public void handleClientMessage(ClientMessage clientMessage, long userId) throws HandleException {
        try {
            final Object messageData = objectMapper.readValue(clientMessage.getContent(), clazz);
            handle(clazz.cast(messageData), userId);
        } catch (IOException | ClassCastException e) {
            throw new HandleException("Unable to read message type " + clientMessage.getType() + "!", e);
        }
    }
}
