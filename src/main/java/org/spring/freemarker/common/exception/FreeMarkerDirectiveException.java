package org.spring.freemarker.common.exception;

/**
 *
 *
 * @date 2018-11-26 21:34:49
 */
public class FreeMarkerDirectiveException extends BusinessException{

    public FreeMarkerDirectiveException(String errorMsg) {
        super("freemarker.directive.exception",errorMsg);
    }

    public FreeMarkerDirectiveException(Object... messageArguments) {
        super("freemarker.directive.exception", messageArguments);
    }

    public FreeMarkerDirectiveException(String message, Object... messageArguments) {
        super("freemarker.directive.exception", message, messageArguments);
    }

    public FreeMarkerDirectiveException(String message, Throwable cause, Object... messageArguments) {
        super("freemarker.directive.exception", message, cause, messageArguments);
    }

    public FreeMarkerDirectiveException(Throwable cause, Object... messageArguments) {
        super("freemarker.directive.exception", cause, messageArguments);
    }

}
