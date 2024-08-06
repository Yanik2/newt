package org.newtframework.testdata.unique;

import org.newtframework.annotation.Component;

@Component
public class TestClassInterfaceDependency {
    private final UniqueTypeInterface uniqueTypeInterface;

    public TestClassInterfaceDependency(UniqueTypeInterface uniqueTypeInterface) {
        this.uniqueTypeInterface = uniqueTypeInterface;
    }
}
