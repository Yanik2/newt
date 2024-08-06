package org.newtframework.processor;

import java.util.Map;

public interface PostProcessor {

    void afterCreate(Map<String, Object> components);
}
