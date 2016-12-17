package ru.aviaj.messagesystem;

import org.springframework.stereotype.Service;
import ru.aviaj.messagesystem.message.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


@Service
public class MessageSystem {

    private final Map<Address, ConcurrentLinkedQueue<Message>> messages = new HashMap<>();

    public void send(Message message) {
        final Queue<Message> msgQueue = messages.get(message.getTo());
        if (msgQueue == null) {
            final ConcurrentLinkedQueue<Message> newQueue = new ConcurrentLinkedQueue<>();
            newQueue.add(message);
            messages.put(message.getTo(), newQueue);

            return;
        }
        msgQueue.add(message);
    }

    public void execForAbonent(Abonent abonent) {
        if (messages.size() != 0) {
            final Queue<Message> msgQueue = messages.get(abonent.getAddress());
            if (msgQueue != null) {
                while (!msgQueue.isEmpty()) {
                    final Message msg = msgQueue.poll();
                    msg.exec(abonent);
                }
            }
        }
    }
}
