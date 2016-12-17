package ru.aviaj.messagesystem.message.authcontroller;

import ru.aviaj.controller.AuthenticationController;
import ru.aviaj.messagesystem.Address;

public class MsgAuthResult extends MsgToAuthController {

    private String sessionId;
    private long userId;

    public MsgAuthResult(Address from, Address to, String sessionId, long userId) {
        super(from, to);
        this.sessionId = sessionId;
        this.userId = userId;
    }

    @Override
    public void exec(AuthenticationController authController) {
        authController.setWaiterStatus(sessionId, userId);
    }
}
