package org.newtframework.testdata.unique;

import org.newtframework.annotation.Component;

@Component
public class TestClassAbstractDependency {
    private final AbstractUniqueType abstractUniqueType;

    public TestClassAbstractDependency(AbstractUniqueType abstractUniqueType) {
        this.abstractUniqueType = abstractUniqueType;
    }
}
