package org.newtframework.componentdefinition;

import java.lang.reflect.Constructor;
import java.util.List;

//TODO make immutable
public class ComponentDefinition {
    private String name;
    private Class<?> type;
    private Constructor<?> constructor;
    private List<ComponentDefinition> dependencies;

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

    public List<ComponentDefinition> getDependencies() {
        return dependencies;
    }

    public void setDependencies(
        List<ComponentDefinition> dependencies) {
        this.dependencies = dependencies;
    }

    public Constructor<?> getConstructor() {
        return constructor;
    }

    public void setConstructor(Constructor<?> constructor) {
        this.constructor = constructor;
    }
}
