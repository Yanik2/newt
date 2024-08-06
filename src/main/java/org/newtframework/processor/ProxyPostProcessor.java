package org.newtframework.processor;

import java.lang.reflect.Proxy;
import java.util.Map;
import org.newtframework.annotation.ProxyComponent;
import org.newtframework.proxy.ProxyInvocationHandler;

public class ProxyPostProcessor implements PostProcessor {
    @Override
    public void afterCreate(Map<String, Object> components) {
        final var keyValueSet = components.entrySet();

        for (Map.Entry<String, Object> entry : keyValueSet) {
            final var type = entry.getValue().getClass();

            if (type.isAnnotationPresent(ProxyComponent.class)) {
                final var proxy = Proxy.newProxyInstance(
                    type.getClassLoader(),
                    new Class[] {type.getInterfaces()[0]},
                    new ProxyInvocationHandler(entry.getValue())
                );
                components.put(entry.getKey(), proxy);
            }
        }
    }
}
