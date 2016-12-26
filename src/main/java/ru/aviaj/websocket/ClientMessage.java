package ru.aviaj.websocket;

@SuppressWarnings("unused")
public class ClientMessage {

    private String type;
    private String content;

    public ClientMessage() { }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public ClientMessage(String type, String content) {
        this.type = type;
        this.content = content;

    }
}
