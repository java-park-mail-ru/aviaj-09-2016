package ru.aviaj.messagesystem.message.authcontroller;

import ru.aviaj.controller.AuthenticationController;
import ru.aviaj.database.exception.DbException;
import ru.aviaj.messagesystem.Address;
import ru.aviaj.service.AccountService;
import ru.aviaj.service.SessionService;

public class MsgLogoutResult extends MsgToAuthController {

    private String sessionId;
    boolean success;

    public MsgLogoutResult(Address from, Address to, String sessionId, boolean success) {
        super(from, to);
        this.sessionId = sessionId;
        this.success = success;
    }

    @Override
    public void exec(AuthenticationController authController) {

    }
}
