package org.newtframework.test.circular;

import org.newtframework.annotation.Component;

@Component
public class CircularOne {
    private final CircularTwo circularTwo;

    public CircularOne(CircularTwo circularTwo) {
        this.circularTwo = circularTwo;
    }
}
