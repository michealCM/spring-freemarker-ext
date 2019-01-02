package org.spring.freemarker.ext.validation.model;

import java.io.Serializable;

/**
 *
 *
 * @date 2018-12-13 13:47:29
 */
public class Error implements Serializable {

    private static final long serialVersionUID = 2695037209189234417L;

    private boolean fieldError = true;

    private String key;

    private String message;

    public Error(String key, String message){
        this.key = key;
        this.message = message;
    }

    public Error(String key, String message, boolean fieldError){
        this.key = key;
        this.message = message;
        this.fieldError = fieldError;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public final boolean isFieldError() {
        return fieldError;
    }
}
