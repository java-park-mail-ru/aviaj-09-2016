package ru.aviaj.mechanics.time.request;

public class GetClientPing {
    public static class Request {

    }

    public static class Response {

        private int clientPing;
        private int clientTimeShift;

        public Response(int clientPing, int clientTimeShift) {
            this.clientPing = clientPing;
            this.clientTimeShift = clientTimeShift;
        }

        public int getClientPing() {
            return clientPing;
        }

        public int getClientTimeShift() {
            return clientTimeShift;
        }

        public static Builder createBuilder() { return new Builder(); }

        public static class Builder {
            private int ping = -1;
            private int timeShift = -1;

            public Builder ping(int ping) {
                this.ping = ping;
                return this;
            }

            public Builder timeShift(int timeShift) {
                this.timeShift = timeShift;
                return this;
            }

            public Response build() { return new Response(ping, timeShift); }
        }
    }


}
