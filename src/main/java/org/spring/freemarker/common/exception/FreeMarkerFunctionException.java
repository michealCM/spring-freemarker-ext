package org.spring.freemarker.common.exception;

/**
 *
 * @date 2018-11-26 21:35:16
 */
public class FreeMarkerFunctionException extends BusinessException {

    public FreeMarkerFunctionException(String errorMsg) {
        super("freemarker.function.exception",errorMsg);
    }

    public FreeMarkerFunctionException(Object... messageArguments) {
        super("freemarker.function.exception", messageArguments);
    }

    public FreeMarkerFunctionException(String message, Object... messageArguments) {
        super("freemarker.function.exception", message, messageArguments);
    }

    public FreeMarkerFunctionException(String message, Throwable cause, Object... messageArguments) {
        super("freemarker.function.exception", message, cause, messageArguments);
    }

    public FreeMarkerFunctionException(Throwable cause, Object... messageArguments) {
        super("freemarker.function.exception", cause, messageArguments);
    }

}
