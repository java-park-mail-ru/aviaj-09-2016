package ru.aviaj.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ClientMessageHandlerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientMessageHandlerService.class);

    final Map<Class<?>, ClientMessageHandler<?>> handlers = new HashMap<>();

    public void handle(ClientMessage clientMessage, long userId) throws HandleException {

        final Class clazz;
        try {
            clazz = Class.forName(clientMessage.getType());
        } catch (ClassNotFoundException e) {
            throw new HandleException("Unable to handle message type " + clientMessage.getType() + "!", e);
        }

        final ClientMessageHandler handler = handlers.get(clazz);
        if (handler == null) {
            throw new HandleException("Unable to find handler for message type " + clientMessage.getType());
        }

        handler.handle(clientMessage, userId);
    }


    public <T> void registerHandler(Class<T> clazz, ClientMessageHandler<T> handler) {
        handlers.put(clazz, handler);
    }
}
