package org.spring.freemarker.common.utils;

/**
 *
 * @date 2018-11-26 17:00:14
 */
public class JSONConverter {

    public <T> T fromString(Class<T> targetClass, String value, Class<?>... elementClasses) {
        return JSONBinder.binder(targetClass, elementClasses).fromJSON(value);
    }

    public <T> T fromString(Class<T> targetClass, byte[] value, Class<?>... elementClasses) {
        return JSONBinder.binder(targetClass, elementClasses).fromJSON(value);
    }

    @SuppressWarnings("unchecked")
    public <T> String toString(T value) {
        return JSONBinder.binder((Class<T>) value.getClass()).toJSON(value);
    }

    @SuppressWarnings("unchecked")
    public <T> byte[] toBytes(T value) {
        return JSONBinder.binder((Class<T>) value.getClass()).toBytes(value);
    }
}
