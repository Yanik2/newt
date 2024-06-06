package org.newtframework.componentdefinition;

public class ComponentDefinition {
    private String name;
    private Class<?> type;

    public ComponentDefinition(String name, Class<?> type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Class<?> getType() {
        return type;
    }
}
