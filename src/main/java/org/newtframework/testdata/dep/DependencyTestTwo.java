package org.newtframework.testdata.dep;

import org.newtframework.annotation.Component;
import org.newtframework.testdata.leve1.LevelOneTestClass;
import org.newtframework.testdata.leve1.level2.LevelTwoTestClass;
import org.newtframework.testdata.leve1.level2.level3.LevelThreeTestClass;

@Component
public class DependencyTestTwo {
    private final LevelOneTestClass levelOneTestClass;
    private final LevelTwoTestClass levelTwoTestClass;
    private final LevelThreeTestClass levelThreeTestClass;

    public DependencyTestTwo(LevelOneTestClass levelOneTestClass,
                             LevelTwoTestClass levelTwoTestClass,
                             LevelThreeTestClass levelThreeTestClass) {
        this.levelOneTestClass = levelOneTestClass;
        this.levelTwoTestClass = levelTwoTestClass;
        this.levelThreeTestClass = levelThreeTestClass;
    }
}
