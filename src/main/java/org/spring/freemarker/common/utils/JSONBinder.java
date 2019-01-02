package org.spring.freemarker.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 *
 * @date 2018-11-26 17:05:53
 * @param <T>
 */
public class JSONBinder<T> {

    private static final String  DATE_FORMAT_DATETIME = "dd/MM/yyyy HH:mm:ss.SSS";

    public static <T> JSONBinder<T> binder(Class<T> beanClass, Class<?>... elementClasses) {
        return new JSONBinder<T>(beanClass, elementClasses);
    }

    public static ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_DATETIME);
        mapper.setConfig(mapper.getSerializationConfig().with(dateFormat));
        mapper.setConfig(mapper.getDeserializationConfig().with(dateFormat));
        return mapper;
    }

    private final Class<T> beanClass;

    private final Class<?>[] elementClasses;

    private JSONBinder(Class<T> beanClass, Class<?>... elementClasses) {
        this.beanClass = beanClass;
        this.elementClasses = elementClasses;
    }

    public T fromJSON(String json) {
        ObjectMapper mapper = createMapper();
        try {
            return elementClasses == null || elementClasses.length == 0 ? mapper.readValue(json, beanClass) : fromJSONToGeneric(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public T fromJSON(byte[] json) {
        ObjectMapper mapper = createMapper();
        try {
            return elementClasses == null || elementClasses.length == 0 ? mapper.readValue(json, beanClass) : fromJSONToGeneric(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private T fromJSONToGeneric(String json) throws IOException {
        ObjectMapper mapper = createMapper();
        return mapper.readValue(json, mapper.getTypeFactory().constructParametricType(beanClass, elementClasses));
    }

    private T fromJSONToGeneric(byte[] json) throws IOException {
        ObjectMapper mapper = createMapper();
        return mapper.readValue(json, mapper.getTypeFactory().constructParametricType(beanClass, elementClasses));
    }

    public String toJSON(T object) {
        ObjectMapper mapper = createMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] toBytes(T object) {
        ObjectMapper mapper = createMapper();
        try {
            return mapper.writeValueAsBytes(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
