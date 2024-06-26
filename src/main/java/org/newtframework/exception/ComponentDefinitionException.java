package org.newtframework.exception;

public class ComponentDefinitionException extends RuntimeException {
    public ComponentDefinitionException(String message, Throwable ex) {
        super(message, ex);
    }

    public ComponentDefinitionException(String message) {
        super(message);
    }
}
