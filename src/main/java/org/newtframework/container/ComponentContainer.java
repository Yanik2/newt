package org.newtframework.container;

import java.util.List;
import org.newtframework.componentdefinition.ComponentDefinition;
import org.newtframework.reader.ComponentReader;

public class ComponentContainer {
    private final ComponentReader componentReader = new ComponentReader();

    private List<ComponentDefinition> componentDefinitions;

    public ComponentContainer(String packages) {
        this.componentDefinitions = componentReader.initializeComponentsDefinitions(packages);
    }

}
