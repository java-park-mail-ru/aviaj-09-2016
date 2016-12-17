package ru.aviaj.messagesystem.message.sessionservice;

import ru.aviaj.database.exception.DbException;
import ru.aviaj.messagesystem.Address;
import ru.aviaj.messagesystem.message.authcontroller.MsgLoginResult;
import ru.aviaj.service.SessionService;

public class MsgLogin extends MsgToSessionService {

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
            final MsgLoginResult resultMsg = new MsgLoginResult(getTo(), getFrom(), sessionId, userId, true);
            sessionService.getMessageSystem().send(resultMsg);
        } catch (DbException e) {
            final MsgLoginResult resultMsg = new MsgLoginResult(getTo(), getFrom(), sessionId, userId, false);
            sessionService.getMessageSystem().send(resultMsg);
        }
    }
}
