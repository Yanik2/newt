package org.newtframework.reader;

import java.lang.reflect.Constructor;
import java.util.LinkedList;
import java.util.List;
import org.newtframework.componentdefinition.ComponentDefinition;
import org.newtframework.exception.ComponentDefinitionException;

public class DependenciesDiscoverer {

    public void discover(List<ComponentDefinition> componentDefinitions) {
        for (ComponentDefinition cd : componentDefinitions) {
            final var constructor = getConstructor(cd);
            final var parameterTypes = constructor.getParameterTypes();
            final var dependencies = new LinkedList<ComponentDefinition>();

            for (Class<?> type : parameterTypes) {
                final var cdDep = componentDefinitions.stream()
                    .filter(componentDefinition -> componentDefinition.getType().equals(type))
                    .findFirst()
                    .orElseThrow(() ->
                        new ComponentDefinitionException("Cannot find dependency of type: " + type
                            + " for component: " + cd.getName()));
                dependencies.add(cdDep);
            }

            cd.setDependencies(dependencies);
            cd.setConstructor(constructor);
        }
    }

    private Constructor<?> getConstructor(ComponentDefinition componentDefinition) {
        final var constructors = componentDefinition.getType().getConstructors();

        if (constructors.length != 1) {
            throw new ComponentDefinitionException("Class should have one public constructor");
        }

        return constructors[0];
    }
}
