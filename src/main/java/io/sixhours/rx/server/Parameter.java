package io.sixhours.rx.server;

/**
 * Value object of the parameter containing parameter type and parameter value.
 */
public class Parameter {

    private final Class<?> type;
    private final Object value;

    /**
     * Instantiates a new {@code Parameter}.
     *
     * @param type  the type
     * @param value the value
     */
    public Parameter(Class<?> type, Object value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public Class<?> getType() {
        return type;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public Object getValue() {
        return value;
    }
}
