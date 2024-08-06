package org.newtframework.reader;

import java.util.Collection;
import java.util.LinkedHashMap;
import org.newtframework.componentdefinition.ComponentDefinition;
import org.newtframework.exception.ComponentDefinitionException;

public class DependenciesDiscoverer {

    public void discover(Collection<ComponentDefinition> componentDefinitions) {
        for (ComponentDefinition cd : componentDefinitions) {
            final var parameterTypes = cd.getConstructor().getParameterTypes();
            final var dependencies = new LinkedHashMap<String, ComponentDefinition>();

            for (Class<?> type : parameterTypes) {
                final var dependency = componentDefinitions.stream()
                    .filter(componentDefinition -> componentDefinition.getHierarchy().contains(type))
                    .toList();

                if (dependency.size() > 1) {
                    throw new ComponentDefinitionException("There is more than one component of type " + type);
                }

                final var cdDep = dependency.stream().findFirst()
                    .orElseThrow(() -> new ComponentDefinitionException(
                        "Cannot find dependency of type: " + type + " for component: " + cd.getName()
                    ));

                dependencies.put(cdDep.getName(), cdDep);
            }

            cd.setDependencies(dependencies);
        }
    }
}
