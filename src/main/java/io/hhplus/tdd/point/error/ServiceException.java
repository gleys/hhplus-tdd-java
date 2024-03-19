package io.hhplus.tdd.point.error;

public class ServiceException extends RuntimeException{
    public ServiceException(final String message) {
        super(message);
    }
}
