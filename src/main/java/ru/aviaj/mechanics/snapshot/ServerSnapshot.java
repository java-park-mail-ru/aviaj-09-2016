package ru.aviaj.mechanics.snapshot;

import java.util.List;

public class ServerSnapshot {

    private List<ServerPlayerSnapshot> players;
    private long serverFrameTime;

    public List<ServerPlayerSnapshot> getPlayers() {
        return players;
    }

    public void setPlayers(List<ServerPlayerSnapshot> players) {
        this.players = players;
    }

    public void setServerFrameTime(long serverFrameTime) {
        this.serverFrameTime = serverFrameTime;
    }

    public long getServerFrameTime() {
        return serverFrameTime;
    }
}
