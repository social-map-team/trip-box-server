package com.socialmap.server.exception;

/**
 * Created by yy on 2/25/15.
 */
public class BusinessLayerException extends RuntimeException {
    public BusinessLayerException() {
        super();
    }

    public BusinessLayerException(String message) {
        super(message);
    }

    public BusinessLayerException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessLayerException(Throwable cause) {
        super(cause);
    }

    protected BusinessLayerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
