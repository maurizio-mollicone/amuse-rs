package it.mollik.amuse.amusers.config;

public final class Constants {
    
    private Constants() {}

    public class Status {
    
        public class Message {

            private Message() {}
    
            public static final String STATUS_MESSAGE_OK = "OK";
            public static final String STATUS_MESSAGE_GENERIC_ERROR = "Generic Error";
    
        }
    
        public class Code {
    
            private Code() {}
    
            public static final int STATUS_CODE_OK = 0;
            public static final int STATUS_CODE_GENERIC_ERROR = 1;
    
        }
    }
}
