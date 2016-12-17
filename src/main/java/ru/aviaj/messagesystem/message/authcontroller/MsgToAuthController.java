package ru.aviaj.messagesystem.message.authcontroller;


import ru.aviaj.controller.AuthenticationController;
import ru.aviaj.messagesystem.Abonent;
import ru.aviaj.messagesystem.Address;
import ru.aviaj.messagesystem.message.Message;

public abstract class MsgToAuthController extends Message {

    public MsgToAuthController(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Abonent abonent) {
        if (abonent instanceof AuthenticationController) {
            exec((AuthenticationController) abonent);
        }
    }

    public abstract void exec(AuthenticationController sessionService);
}
