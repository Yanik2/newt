package org.newtframework.testdata.dep;

import org.newtframework.annotation.Component;
import org.newtframework.testdata.TestClass;

@Component
public class DependencyTestOne {
    private final TestClass testClass;

    public DependencyTestOne(TestClass testClass) {
        this.testClass = testClass;
    }
}
