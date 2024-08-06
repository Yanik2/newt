package org.newtframework.componentdefinition;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ComponentDefinition {
    private final String name;
    private final String fullName;
    private final Class<?> type;
    private final Set<Class<?>> hierarchy;
    private final Constructor<?> constructor;
    private Map<String, ComponentDefinition> dependencies;

    public ComponentDefinition(String name,
                               String fullName,
                               Class<?> type,
                               Set<Class<?>> hierarchy,
                               Constructor<?> constructor) {
        this.name = name;
        this.fullName = fullName;
        this.type = type;
        this.hierarchy = hierarchy;
        this.constructor = constructor;
    }

    public String getName() {
        return name;
    }

    public Class<?> getType() {
        return type;
    }

    public Map<String, ComponentDefinition> getDependencies() {
        return dependencies;
    }

    public Set<Class<?>> getHierarchy() {
        return hierarchy;
    }

    public void setDependencies(Map<String, ComponentDefinition> dependencies) {
        this.dependencies = dependencies;
    }

    public Constructor<?> getConstructor() {
        return constructor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ComponentDefinition that = (ComponentDefinition) o;
        return Objects.equals(name, that.name) && Objects.equals(type, that.type) &&
            Objects.equals(hierarchy, that.hierarchy) &&
            Objects.equals(constructor, that.constructor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, hierarchy, constructor);
    }
}
