package ru.aviaj.mechanics.time.request;

public class GetClientPing {
    public static class Request {

    }

    public static class Response {

        private long clientPing;
        private long clientTimeShift;

        public Response(long clientPing, long clientTimeShift) {
            this.clientPing = clientPing;
            this.clientTimeShift = clientTimeShift;
        }

        public long getClientPing() {
            return clientPing;
        }

        public long getClientTimeShift() {
            return clientTimeShift;
        }

        public static Builder createBuilder() { return new Builder(); }

        public static class Builder {
            private long ping = -1;
            private long timeShift = -1;

            public Builder ping(long ping) {
                this.ping = ping;
                return this;
            }

            public Builder timeShift(long timeShift) {
                this.timeShift = timeShift;
                return this;
            }

            public Response build() { return new Response(ping, timeShift); }
        }
    }


}
