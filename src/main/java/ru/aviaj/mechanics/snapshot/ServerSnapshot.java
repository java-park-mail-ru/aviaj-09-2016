package ru.aviaj.mechanics.snapshot;

import java.util.List;

@SuppressWarnings("unused")
public class ServerSnapshot {

    private List<ServerPlayerSnapshot> players;
    private int serverFrameTime;

    public List<ServerPlayerSnapshot> getPlayers() {
        return players;
    }

    public void setPlayers(List<ServerPlayerSnapshot> players) {
        this.players = players;
    }

    public void setServerFrameTime(int serverFrameTime) {
        this.serverFrameTime = serverFrameTime;
    }

    public int getServerFrameTime() {
        return serverFrameTime;
    }
}
