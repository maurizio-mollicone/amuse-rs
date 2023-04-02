package it.mollik.amuse.amusers.config;

import org.springframework.beans.factory.annotation.Value;

public final class Constants {

    public static final String DEFAULT_IP_ADDRESS = "0.0.0.0";
    public static final String SYSTEM_USER = "system";

    public static final String JWT_BLACKLIST_CACHE_KEY = "jwtBlacklist";
    public static final String USERS_CACHE_KEY = "users";

	public static final String INCLUE_ALL = "/**";

    @Value("${server.servlet.context-path}")
    public static final String CTX = "/amuse";

    private Constants() {
    }

    public class Api {
        
        private Api() {
        }
        
        
        public static final String TEST_API = "/test";
        public static final String USERS_API = "/users";
        public static final String AUTH_API = "/auth";
        public static final String BOOKS_API = "/books";
        public static final String AUTHORS_API = "/authors";


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
