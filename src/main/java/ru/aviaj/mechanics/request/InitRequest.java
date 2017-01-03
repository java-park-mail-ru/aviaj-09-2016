package ru.aviaj.mechanics.request;

import ru.aviaj.mechanics.snapshot.ServerPlayerSnapshot;

import java.util.List;
import java.util.Map;

public class InitRequest {

    @SuppressWarnings("unused")
    public static class Request {
        private long userId;
        private List<ServerPlayerSnapshot> players;
        private Map<Long, String> playerNames;

        public long getUserId() {
            return userId;
        }

        public List<ServerPlayerSnapshot> getPlayers() {
            return players;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public void setPlayers(List<ServerPlayerSnapshot> players) {
            this.players = players;
        }

        public void setPlayerNames(Map<Long, String> playerNames) {
            this.playerNames = playerNames;
        }

        public Map<Long, String> getPlayerNames() {
            return playerNames;

        }
    }
}
