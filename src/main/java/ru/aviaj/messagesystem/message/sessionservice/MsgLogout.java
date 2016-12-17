package ru.aviaj.messagesystem.message.sessionservice;

import ru.aviaj.database.exception.DbException;
import ru.aviaj.messagesystem.Address;
import ru.aviaj.messagesystem.message.authcontroller.MsgLogoutResult;
import ru.aviaj.service.SessionService;

public class MsgLogout extends MsgToSessionService {

    private String sessionId;

    public MsgLogout(Address from, Address to, String sessionId) {
        super(from, to);
        this.sessionId = sessionId;
    }

    @Override
    public void exec(SessionService sessionService) {
        try {
            sessionService.removeSession(this.sessionId);
            final MsgLogoutResult resultMsg = new MsgLogoutResult(getTo(), getFrom(), sessionId, true);
            sessionService.getMessageSystem().send(resultMsg);
        } catch (DbException e) {
            final MsgLogoutResult resultMsg = new MsgLogoutResult(getTo(), getFrom(), sessionId, false);
            sessionService.getMessageSystem().send(resultMsg);
        }
    }
}
