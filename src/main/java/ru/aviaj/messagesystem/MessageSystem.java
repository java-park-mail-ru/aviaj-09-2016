package ru.aviaj.messagesystem;

import ru.aviaj.messagesystem.message.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageSystem {

    private final Map<Address, ConcurrentLinkedQueue<Message>> messages = new HashMap<>();

    public void send(Message message) {
        final Queue<Message> msgQueue = messages.get(message.getTo());
        msgQueue.add(message);
    }

    public void execForAbonent(Abonent abonent) {
        final Queue<Message> msgQueue = messages.get(abonent.getAddress());
        while (!msgQueue.isEmpty()) {
            final Message msg = msgQueue.poll();
            msg.exec(abonent);
        }
    }
}
