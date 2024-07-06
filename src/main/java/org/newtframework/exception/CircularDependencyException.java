package org.newtframework.exception;

import org.newtframework.componentdefinition.ComponentDefinition;

import java.util.List;

public class CircularDependencyException extends RuntimeException {
    private final List<ComponentDefinition> definitions;

    public CircularDependencyException(List<ComponentDefinition> definitions, String message) {
        super(message);
        this.definitions = definitions;
    }
}
