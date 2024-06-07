package org.newtframework.test.dep;

import org.newtframework.annotation.Component;
import org.newtframework.test.TestClass;

@Component
public class DependencyTestOne {
    private final TestClass testClass;

    public DependencyTestOne(TestClass testClass) {
        this.testClass = testClass;
    }
}
