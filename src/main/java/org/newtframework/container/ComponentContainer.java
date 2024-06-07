package org.newtframework.container;

import java.util.List;
import java.util.Map;
import org.newtframework.componentdefinition.ComponentDefinition;
import org.newtframework.initializer.ComponentsInitializer;
import org.newtframework.reader.ComponentReader;

public class ComponentContainer {
    private final ComponentReader componentReader;
    private final ComponentsInitializer componentsInitializer;

    private List<ComponentDefinition> componentDefinitions;
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

}
