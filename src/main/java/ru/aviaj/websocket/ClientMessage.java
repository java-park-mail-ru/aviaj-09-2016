package ru.aviaj.websocket;

public class ClientMessage {

    private String type;
    private String content;

    public ClientMessage() { }

    public ClientMessage(String type, String content) {
        this.type = type;
        this.content = content;
    }
}
