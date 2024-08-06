package org.newtframework;

import java.lang.reflect.Type;
import org.newtframework.container.ComponentContainer;
import org.newtframework.testdata.annotationname.TestClass;
import org.newtframework.testdata.unique.TypeInterface;

public class Main {
    public static void main(String[] args) {
        final var container = new ComponentContainer("org.newtframework.testdata");
        TestClass component = (TestClass) container.getComponent("Test class name");
        TypeInterface component2 = container.getComponent(TypeInterface.class);

        System.out.println();
    }
}