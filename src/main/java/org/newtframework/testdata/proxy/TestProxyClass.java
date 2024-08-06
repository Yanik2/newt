package org.newtframework.testdata.proxy;

import org.newtframework.annotation.Component;
import org.newtframework.annotation.Log;
import org.newtframework.annotation.ProxyComponent;

@Component
@ProxyComponent
public class TestProxyClass implements ProxyInterface {

    @Log
    public String getString() {
        System.out.println("Original object: method getString invoked");
        return "return string";
    }
}
