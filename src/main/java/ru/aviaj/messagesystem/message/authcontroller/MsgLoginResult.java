package ru.aviaj.messagesystem.message.authcontroller;

import ru.aviaj.controller.AuthenticationController;
import ru.aviaj.messagesystem.Address;

public class MsgLoginResult extends MsgToAuthController {

    private String sessionId;
    private long userId;
    boolean success;

    public MsgLoginResult(Address from, Address to, String sessionId, long userId, boolean success) {
        super(from, to);
        this.sessionId = sessionId;
        this.userId = userId;
        this.success = success;
    }

    @Override
    public void exec(AuthenticationController authController) {

    }
}
