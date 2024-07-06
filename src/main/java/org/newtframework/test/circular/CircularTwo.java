package org.newtframework.test.circular;

import org.newtframework.annotation.Component;

@Component
public class CircularTwo {
    private final CircularThree circularThree;

    public CircularTwo(CircularThree circularThree) {
        this.circularThree = circularThree;
    }
}
