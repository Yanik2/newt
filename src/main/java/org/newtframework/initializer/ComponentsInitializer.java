package org.newtframework.initializer;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.newtframework.componentdefinition.ComponentDefinition;
import org.newtframework.exception.CircularDependencyException;
import org.newtframework.exception.ComponentCreationException;

public class ComponentsInitializer {

    public Map<String, Object> initialize(Map<String, ComponentDefinition> definitions) {
        final var components = new HashMap<String, Object>();

        checkForCircularDependencies(definitions.values(), 0, definitions.size());
        initialize(definitions, components);

        return components;
    }

    private void checkForCircularDependencies(Collection<ComponentDefinition> definitions, int currentDepth, int depth) {
        if (currentDepth == depth) {
            if (definitions.isEmpty()) {
                return;
            } else {
                System.out.println("Circular dependency in: " + definitions.stream()
                        .map(ComponentDefinition::getName)
                        .collect(Collectors.joining("[", ",", "]")));
                throw new CircularDependencyException(definitions, "Circular dependency");
            }
        }

        for (ComponentDefinition componentDefinition : definitions) {
            final var dependencies = componentDefinition.getDependencies().values();
            checkForCircularDependencies(dependencies, currentDepth + 1, depth);
        }
    }

    private void initialize(Map<String, ComponentDefinition> definitions,
                            Map<String, Object> components) {
        for (ComponentDefinition definition : definitions.values()) {
            if (components.containsKey(definition.getName())) {
                continue;
            }

            if (definition.getDependencies().isEmpty()) {
                if (definition.getConstructor().getParameterCount() == 0) {
                    components.put(definition.getName(),
                            initializeComponent(definition.getConstructor()));
                } else {
                    throw new ComponentCreationException(
                            "Cannot find dependencies for bean definition: "
                                    + definition.getName() + " with constructor: " +
                                    definition.getConstructor());
                }
            } else {
                initialize(definition.getDependencies(), components);
                final var dependencies = definition.getDependencies()
                    .values()
                    .stream()
                        .map(dep -> components.get(dep.getName()))
                        .toArray();

                components.put(definition.getName(),
                        initializeComponent(definition.getConstructor(), dependencies));
            }
        }
    }

    private Object initializeComponent(Constructor<?> constructor, Object... dependencies) {
        try {
            return constructor.newInstance(dependencies);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error during creating component with constructor "
                    + constructor);
        }
    }
}
