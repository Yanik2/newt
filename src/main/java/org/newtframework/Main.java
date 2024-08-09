package org.newtframework;

import org.newtframework.container.ComponentContainer;

public class Main {
    public static void main(String[] args) {
        final var container = new ComponentContainer("org.newtframework.testdata.injectbyname");

        System.out.println();
    }
}