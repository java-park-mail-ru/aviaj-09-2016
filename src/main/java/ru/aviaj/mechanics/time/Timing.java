package ru.aviaj.mechanics.time;

public class Timing {

    private final long clientPing;
    private final long clientTimeshift;

    public Timing(long clientPing, long clientTimeshift) {
        this.clientPing = clientPing;
        this.clientTimeshift = clientTimeshift;
    }

    public long getClientPing() {
        return clientPing;
    }

    public long getClientTimeshift() {
        return clientTimeshift;
    }
}
