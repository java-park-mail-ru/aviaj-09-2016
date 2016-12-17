package ru.aviaj.messagesystem.message.sessionservice;

import ru.aviaj.database.exception.DbException;
import ru.aviaj.messagesystem.Address;
import ru.aviaj.service.SessionService;

public class MsgAuth extends MsgToSessionService {

    private String sessionId;

    public MsgAuth(Address from, Address to, String sessionId) {
        super(from, to);
        this.sessionId = sessionId;
    }

    @Override
    public void exec(SessionService sessionService) {
        try {
            sessionService.getUserIdBySession(this.sessionId);
        } catch (DbException e) {
            //Error back
        }
        //Success back
    }
}
