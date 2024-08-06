package org.newtframework.container;

import java.util.Map;
import org.newtframework.componentdefinition.ComponentDefinition;
import org.newtframework.exception.ComponentDefinitionException;
import org.newtframework.exception.ComponentIsNotPresentException;
import org.newtframework.initializer.ComponentsInitializer;
import org.newtframework.reader.ComponentReader;

public class ComponentContainer {
    private final ComponentReader componentReader;
    private final ComponentsInitializer componentsInitializer;

    private Map<String, ComponentDefinition> componentDefinitions;
    private Map<String, Object> components;

    public ComponentContainer(String packages) {
        this();
        this.componentDefinitions = componentReader.initializeComponentsDefinitions(packages);
        this.components = componentsInitializer.initialize(componentDefinitions);
    }

    public ComponentContainer() {
        this.componentReader = new ComponentReader();
        this.componentsInitializer = new ComponentsInitializer();
    }

    public Object getComponent(String componentName) {
        return components.get(componentName);
    }

    @SuppressWarnings("unchecked")
    public <T> T getComponent(Class<T> type) {
        final var definitions = componentDefinitions.values()
            .stream()
            .filter(cd -> cd.getHierarchy().contains(type))
            .toList();

        if (definitions.size() > 1) {
            throw new ComponentDefinitionException("More than one bean present in context, please specify name");
        }

        final var name = definitions.stream().findFirst()
            .map(ComponentDefinition::getName)
            .orElseThrow(() -> new ComponentIsNotPresentException("Component of type " + type + " is not present in context"));

        return (T) getComponent(name);
    }

    @SuppressWarnings("unchecked")
    public <T> T getComponent(String componentName, Class<T> type) {
        return (T) getComponent(componentName);
    }

}
