package org.newtframework.initializer;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.newtframework.componentdefinition.ComponentDefinition;
import org.newtframework.exception.ComponentCreationException;

public class ComponentsInitializer {

    public Map<String, Object> initialize(List<ComponentDefinition> definitions) {
        final var components = new HashMap<String, Object>();

        initialize(definitions, components);

        return components;
    }

    private void initialize(List<ComponentDefinition> definitions,
                            Map<String, Object> components) {
        for (ComponentDefinition definition : definitions) {
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
                final var dependencies = definition.getDependencies().stream()
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
            throw new RuntimeException("Unexpected error during creating component with constructor"
                + constructor);
        }
    }
}
