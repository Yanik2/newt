package org.newtframework.initializer;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.newtframework.componentdefinition.ComponentDefinition;
import org.newtframework.exception.ComponentCreationException;

public class ComponentsInitializer {

    public Map<String, Object> initialize(List<ComponentDefinition> definitions) {
        final var components = new HashMap<String, Object>();

        for (ComponentDefinition definition : definitions) {
            components.put(definition.getName(), initializeComponent(definition.getType()));
        }

        return components;
    }

    private Object initializeComponent(Class<?> clazz) {
        try {
            final var constructor = clazz.getDeclaredConstructor();
            if (Modifier.isPublic(constructor.getModifiers())) {
                return constructor.newInstance();
            } else {
                throw new ComponentCreationException("Class : " + clazz + " should have public default constructor");
            }
        } catch (Exception e) {
            throw new ComponentCreationException("Error while creating component", e);
        }
    }
}
