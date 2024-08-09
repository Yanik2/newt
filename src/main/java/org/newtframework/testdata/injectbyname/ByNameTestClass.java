package org.newtframework.testdata.injectbyname;

import org.newtframework.annotation.Component;
import org.newtframework.annotation.Qualifier;

@Component
public class ByNameTestClass {
    private final Injectable injectable;


    public ByNameTestClass(@Qualifier("injectableImplTwo") Injectable injectable) {
        this.injectable = injectable;
    }
}
