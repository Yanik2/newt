package org.newtframework.exception;

import java.util.Collection;
import org.newtframework.componentdefinition.ComponentDefinition;

public class CircularDependencyException extends RuntimeException {
    private final Collection<ComponentDefinition> definitions;

    public CircularDependencyException(Collection<ComponentDefinition> definitions, String message) {
        super(message);
        this.definitions = definitions;
    }
}
