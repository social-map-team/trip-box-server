package com.socialmap.server.exception;

/**
 * Created by yy on 2/25/15.
 */
public class ServerInitializationException extends BusinessLayerException {
    public ServerInitializationException() {
        super();
    }

    public ServerInitializationException(String message) {
        super(message);
    }

    public ServerInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerInitializationException(Throwable cause) {
        super(cause);
    }

    protected ServerInitializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
