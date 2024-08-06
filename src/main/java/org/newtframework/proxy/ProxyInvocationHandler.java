package org.newtframework.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.stream.Stream;
import org.newtframework.annotation.Log;

public class ProxyInvocationHandler implements InvocationHandler {
    private final Object originalObject;

    public ProxyInvocationHandler(Object originalObject) {
        this.originalObject = originalObject;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        Method originalMethod;
        if (objects != null) {
            final var parameterTypes = Stream.of(objects).map(Object::getClass).toList();
            final var classArray = new Class[parameterTypes.size()];
            originalMethod =
                originalObject.getClass().getMethod(method.getName(), parameterTypes.toArray(classArray));
        } else {
            originalMethod = originalObject.getClass().getMethod(method.getName());
        }

        if (originalMethod.isAnnotationPresent(Log.class)) {
            System.out.println("Proxy: Start call for method: " + method);
            final var result = method.invoke(originalObject, objects);
            System.out.println("Proxy: Finish. Result: " + result);
            return result;
        } else {
            return method.invoke(originalObject, objects);
        }
    }
}
