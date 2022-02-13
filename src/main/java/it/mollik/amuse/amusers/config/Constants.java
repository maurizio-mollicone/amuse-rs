package it.mollik.amuse.amusers.config;

public final class Constants {

    public static final String DEFAULT_IP_ADDRESS = "0.0.0.0";

    public static final String JWT_BLACKLIST_CACHE_KEY = "jwtBlacklist";
    public static final String USERS_CACHE_KEY = "users";

    private Constants() {
    }

    public class Jwt {
        
        private Jwt() {
        }

        public static final String ROLES_CLAIM_KEY = "roles";
        public static final String USER_AGENT_CLAIM_KEY = "userAgent";
        public static final String CLIENT_IP_CLAIM_KEY = "clientIp";

    }

    public class Status {

        public class Message {

            private Message() {
            }

            public static final String STATUS_MESSAGE_OK = "OK";
            public static final String STATUS_MESSAGE_GENERIC_ERROR = "Generic Error";

        }

        public class Code {

            private Code() {
            }

            public static final int STATUS_CODE_OK = 0;
            public static final int STATUS_CODE_GENERIC_ERROR = 1;

        }
    }
}
