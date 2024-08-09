package org.newtframework.reader;

import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.LinkedHashMap;
import org.newtframework.annotation.Qualifier;
import org.newtframework.componentdefinition.ComponentDefinition;
import org.newtframework.exception.ComponentDefinitionException;

public class DependenciesDiscoverer {

    public void discover(Collection<ComponentDefinition> componentDefinitions) {
        for (ComponentDefinition cd : componentDefinitions) {
            final var parameters = cd.getConstructor().getParameters();
            final var dependencies = new LinkedHashMap<String, ComponentDefinition>();

            for (Parameter param : parameters) {
                final ComponentDefinition componentDefinition;
                if (param.isAnnotationPresent(Qualifier.class)) {
                    componentDefinition = addByQualifier(componentDefinitions, param);
                } else {
                    componentDefinition = addByType(componentDefinitions, param);
                }

                dependencies.put(componentDefinition.getName(), componentDefinition);
            }

            cd.setDependencies(dependencies);
        }
    }

    private ComponentDefinition addByQualifier(Collection<ComponentDefinition> componentDefinitions,
                                     Parameter parameter) {
        final var dependencyName = parameter.getAnnotation(Qualifier.class).value();

        return componentDefinitions.stream()
            .filter(cd -> cd.getName().equalsIgnoreCase(dependencyName))
            .findFirst()
            .orElseThrow(
                () -> new ComponentDefinitionException("Cannot find dependency " +
                    "of type: " + parameter.getType() + ", and name: " + parameter.getName()));
    }

    private ComponentDefinition addByType(Collection<ComponentDefinition> componentDefinitions,
                           Parameter parameter) {
        final var dependency = componentDefinitions.stream()
            .filter(componentDefinition -> componentDefinition.getHierarchy().contains(parameter.getType()))
            .toList();

        if (dependency.size() > 1) {
            throw new ComponentDefinitionException("More than one components of type: " +
                parameter.getType() + ", and no @Qualifier annotation present");
        }

        return dependency.stream()
            .findFirst()
            .orElseThrow(() -> new ComponentDefinitionException("Cannot find dependency for of type: " + parameter.getType()));
    }
}
