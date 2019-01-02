package org.spring.freemarker.ext.validation.model;

import java.util.LinkedHashMap;

/**
 *
 *
 * @date 2018-12-14 14:28:45
 */
public class ValidationResult extends LinkedHashMap<String, Error> {

    private static final long serialVersionUID = 5472327531330099385L;

    public void addError(Error error) {
        this.put(error.getKey(), error);
    }
}
