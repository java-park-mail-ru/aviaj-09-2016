package ru.aviaj.messagesystem.message.authcontroller;

import ru.aviaj.controller.AuthenticationController;
import ru.aviaj.messagesystem.Address;

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
        if (success)
            authController.setWaiterStatus(sessionId, AuthenticationController.LOGOUTUSER);
        else
            authController.setWaiterStatus(sessionId, AuthenticationController.ERRORUSER);
    }
}
