package io.sixhours.rx;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Action that invokes resource method based on given method signature and parameter values.
 */
public class Action {

    private final String clazz;
    private final String methodName;
    private final List<Parameter> parameters = new ArrayList<>();

    /**
     * Instantiates a new {@code Action}.
     *
     * @param signature   the method signature
     * @param paramValues the method parameter values
     * @param body        the body
     */
    public Action(String signature, Map<String, String> paramValues, String body) {

        this.clazz = MethodSignatureUtil.className(signature);
        this.methodName = MethodSignatureUtil.methodName(signature);

        MethodSignatureUtil.arguments(signature).forEach((paramName, paramType) -> {
            try {
                final String value = (body != null && paramValues.get(paramName) == null) ? body : paramValues.get(paramName);

                this.parameters.add(new Parameter(Class.forName(paramType), value));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });


    }

    /**
     * Invokes method of the resource.
     *
     * @return the response
     */
    public Response invoke() {
        try {
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

}
