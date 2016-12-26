package ru.aviaj.mechanics.time.request;

public class ClientPingData {

    public static class Request {
        private final long id;

        public Request(long id) {
            this.id = id;
        }

        public long getId() {
            return id;
        }

        public static Builder createBuilder() {
            return new Builder();
        }

        public static class Builder {
            private long id = -1;

            public Builder id(long id) {
                this.id = id;
                return this;
            }

            public Request build() {
                return new Request(id);
            }
        }
    }
}
