package ru.aviaj.messagesystem.message.authcontroller;

import ru.aviaj.messagesystem.Abonent;
import ru.aviaj.messagesystem.Address;
import ru.aviaj.messagesystem.message.Message;
import ru.aviaj.service.SessionService;

public abstract class MsgToAuthController extends Message {

    public MsgToAuthController(Address from, Address to) {
        super(from, to);
    }

    public void exec(Abonent abonent) {
        if (abonent instanceof SessionService) {
            exec((SessionService) abonent);
        }
    }

    public abstract void exec(SessionService sessionService);
    
}
