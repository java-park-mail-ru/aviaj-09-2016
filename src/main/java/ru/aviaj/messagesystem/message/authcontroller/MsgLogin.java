package ru.aviaj.messagesystem.message.authcontroller;

import ru.aviaj.database.exception.DbException;
import ru.aviaj.messagesystem.Address;
import ru.aviaj.service.SessionService;

public class MsgLogin extends MsgToAuthController {

    private String sessionId;
    private long userId;

    public MsgLogin(Address from, Address to, String sessionId, long userId) {
        super(from, to);
        this.sessionId = sessionId;
        this.userId = userId;
    }

    @Override
    public void exec(SessionService sessionService) {
        try {
            sessionService.addSession(this.sessionId, this.userId);
        } catch (DbException e) {
            //Error back
        }
        //Success back
    }
}
