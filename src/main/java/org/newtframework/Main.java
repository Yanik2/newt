package org.newtframework;

import org.newtframework.container.ComponentContainer;
import org.newtframework.testdata.proxy.ProxyInterface;
import org.newtframework.testdata.proxy.TestProxyClass;

public class Main {
    public static void main(String[] args) {
        final var container = new ComponentContainer("org.newtframework.testdata");
        final var component = container.getComponent(ProxyInterface.class);
        final var string = component.getString();

        System.out.println();
    }
}