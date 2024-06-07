package org.newtframework.test.dep;

import org.newtframework.annotation.Component;

@Component
public class DependencyTestThree {
    private final DependencyTestOne dependencyTestOne;
    private final DependencyTestTwo dependencyTestTwo;

    public DependencyTestThree(DependencyTestOne dependencyTestOne,
                               DependencyTestTwo dependencyTestTwo) {
        this.dependencyTestOne = dependencyTestOne;
        this.dependencyTestTwo = dependencyTestTwo;
    }
}
