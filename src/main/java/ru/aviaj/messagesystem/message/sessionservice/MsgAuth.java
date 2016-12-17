package ru.aviaj.messagesystem.message.sessionservice;

import ru.aviaj.database.exception.DbException;
import ru.aviaj.messagesystem.Address;
import ru.aviaj.messagesystem.message.authcontroller.MsgAuthResult;
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
            final long userId = sessionService.getUserIdBySession(this.sessionId);
            final MsgAuthResult resultMsg = new MsgAuthResult(getTo(), getFrom(), sessionId, userId);
            sessionService.getMessageSystem().send(resultMsg);
        } catch (DbException e) {
            final MsgAuthResult resultMsg = new MsgAuthResult(getTo(), getFrom(), sessionId, -1);
            sessionService.getMessageSystem().send(resultMsg);
        }
    }
}
