package ru.aviaj.messagesystem.message.authcontroller;

import ru.aviaj.database.exception.DbException;
import ru.aviaj.messagesystem.Address;
import ru.aviaj.service.SessionService;

public class MsgLogout extends MsgToAuthController {

    private String sessionId;

    public MsgLogout(Address from, Address to, String sessionId, long userId) {
        super(from, to);
        this.sessionId = sessionId;
    }

    @Override
    public void exec(SessionService sessionService) {
        try {
            sessionService.removeSession(this.sessionId);
        } catch (DbException e) {
            //Error back
        }
        //Success back
    }
}
