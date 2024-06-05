package org.newtframework.reader;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import org.newtframework.annotation.Component;
import org.newtframework.componentdefinition.ComponentDefinition;
import org.newtframework.exception.ComponentDefinitionException;

public class ComponentReader {

    public List<ComponentDefinition> initializeComponentsDefinitions(String packageName) {
        final var uri = getPackageUri(packageName);
        final var fileNames = getClassNames(Paths.get(uri), packageName);
        return fileNames.stream()
            .map(fileName -> {
                try {
                    return Class.forName(fileName);
                } catch (ClassNotFoundException e) {
                    throw new ComponentDefinitionException("Cannot create class for name: " + fileName, e);
                }
            })
            .filter(c -> c.isAnnotationPresent(Component.class))
            .map(c -> new ComponentDefinition(c.getName(), c))
            .toList();
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
        }
    }
}
