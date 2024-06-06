package org.newtframework.exception;

public class ComponentCreationException extends RuntimeException {
    public ComponentCreationException(String message, Throwable ex) {
        super(message, ex);
    }

    public ComponentCreationException(String message) {
        super(message);
    }
}
