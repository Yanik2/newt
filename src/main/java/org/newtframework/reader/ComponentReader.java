package org.newtframework.reader;

import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collectors.toMap;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.newtframework.annotation.Component;
import org.newtframework.componentdefinition.ComponentDefinition;
import org.newtframework.exception.ComponentDefinitionException;

public class ComponentReader {

    private final DependenciesDiscoverer dependenciesDiscoverer;

    public ComponentReader() {
        this.dependenciesDiscoverer = new DependenciesDiscoverer();
    }

    public Map<String, ComponentDefinition> initializeComponentsDefinitions(String packageName) {
        final var uri = getPackageUri(packageName);
        final var fileNames = getClassNames(Paths.get(uri), packageName);
        final var componentDefinitions = fileNames.stream()
            .map(fileName -> {
                try {
                    return Class.forName(fileName);
                } catch (ClassNotFoundException e) {
                    throw new ComponentDefinitionException(
                        "Cannot create class for name: " + fileName, e);
                }
            })
            .filter(c -> c.isAnnotationPresent(Component.class))
            .map(c -> {
                    final var value = c.getAnnotation(Component.class).value();
                    final var name = value.isEmpty() ? c.getSimpleName() : value;
                    return new ComponentDefinition(name,
                        c.getName(),
                        c,
                        unmodifiableSet(getHierarchyForType(c)),
                        getConstructor(c));
                }
            )
            .collect(toMap(
                cd -> cd.getName(),
                cd -> cd,
                (cd1, cd2) -> {
                    throw new ComponentDefinitionException("Component with name " + cd1.getName() +
                        " already exists. Component name should be unique");
                }
            ));

        dependenciesDiscoverer.discover(componentDefinitions.values());
        return componentDefinitions;
    }

    private Set<Class<?>> getHierarchyForType(Class<?> type) {
        final var interfaces = type.getInterfaces();

        final var result = new HashSet<Class<?>>();
        result.add(type);

        for (Class<?> typeInterface : interfaces) {
            result.addAll(getHierarchyForType(typeInterface));
        }

        final var superClass = type.getSuperclass();
        if (superClass != null && !Object.class.equals(superClass)) {
            result.addAll(getHierarchyForType(superClass));
        }

        return result;
    }

    private Constructor<?> getConstructor(Class<?> type) {
        final var constructors = type.getConstructors();

        if (constructors.length != 1) {
            throw new ComponentDefinitionException("Class should have one public constructor");
        }

        return constructors[0];
    }

    private List<String> getClassNames(Path path, String packageName) {
        try {
            final var classNames = new LinkedList<String>();
            getClassNames(path, packageName, classNames);
            return classNames;
        } catch (IOException e) {
            throw new ComponentDefinitionException("I/O error in component reader", e);
        }
    }

    private void getClassNames(Path path, String packageName, List<String> accumulator)
        throws IOException {
        final var files = Files.list(path).toList();

        for (Path file : files) {
            if (Files.isDirectory(file)) {
                getClassNames(file, packageName + "." + file.getFileName().toString(), accumulator);
            } else if (Files.isRegularFile(file)) {
                final var filename = file.getFileName().toString();
                if (filename.endsWith(".class")) {
                    accumulator.add(packageName + "." + filename.replace(".class", ""));
                }
            }
        }
    }

    private URI getPackageUri(String packageName) {
        final var packagesPath = packageName.replace(".", "/");
        try {
            return Thread.currentThread()
                .getContextClassLoader()
                .getResource(packagesPath).toURI();
        } catch (URISyntaxException e) {
            throw new ComponentDefinitionException("URI syntax error", e);
        } catch (NullPointerException ex) {
            throw new ComponentDefinitionException("Cannot resolve path: " + packagesPath);
        }
    }
}
