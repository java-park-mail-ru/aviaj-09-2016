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

        @SuppressWarnings({"InnerClassTooDeeplyNested", "InstanceMethodNamingConvention", "ParameterHidesMemberVariable", "MethodParameterNamingConvention", "InstanceVariableNamingConvention"})
        public static class Builder {
            private long _id = -1;

            public Builder id(long _id) {
                this._id = _id;
                return this;
            }

            public Request build() {
                return new Request(_id);
            }
        }
    }

    @SuppressWarnings("unused")
    public static final class Response {
        private long id;
        private long timeStamp;

        public long getId() {
            return id;
        }

        public long getTimeStamp() {
            return timeStamp;
        }
    }
}
