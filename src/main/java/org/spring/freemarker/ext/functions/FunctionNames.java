package org.spring.freemarker.ext.functions;

/**
 *
 * @date 2018-11-26 16:29:05
 */
public enum FunctionNames {

    FUNCTION_URL("url"),
    FUNCTION_NUMBER_FORMAT("numberFormat");

    private String value;

    private FunctionNames(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
