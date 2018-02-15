package io.sixhours.rx.server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Action that invokes resource method based on given method signature and parameter values.
 */
public class Action {

    private final String clazz;
    private final String methodName;
    private final Map<String, String> arguments;

    /**
     * Instantiates a new {@code Action}.
     *
     * @param signature   the method signature
     */
    public Action(String signature) {
        this.clazz = MethodSignatureUtil.className(signature);
        this.methodName = MethodSignatureUtil.methodName(signature);
        this.arguments = MethodSignatureUtil.arguments(signature);
    }

    /**
     * Invokes method of the resource.
     *
     * @return the response
     */
    public Response invoke(Map<String, String> paramValues, String body) {
        try {
            final List<Parameter> parameters = toParameters(paramValues, body);

            final Class<?>[] classes = parameters.stream().map(Parameter::getType).toArray(size -> new Class<?>[size]);
            final Object[] values = parameters.stream().map(Parameter::getValue).toArray(size -> new Object[size]);

            final Class<?> clazz = Class.forName(this.clazz);
            final Method method = clazz.getDeclaredMethod(this.methodName, classes);
            final Response response = (Response) method.invoke(clazz.getDeclaredConstructor().newInstance(), values);

            return response;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Parameter> toParameters(Map<String, String> paramValues, String body) {
        final List<Parameter> parameters = new ArrayList<>();
        this.arguments.forEach((paramName, paramType) -> {
            try {
                final String value = (body != null && paramValues.get(paramName) == null) ? body : paramValues.get(paramName);

                parameters.add(new Parameter(Class.forName(paramType), value));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        return parameters;
    }

}
