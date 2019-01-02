package org.spring.freemarker.common.exception;

/**
 *
 * @date 2018-11-27 09:51:57
 */
public class FreeMarkerException extends BusinessException {

    public FreeMarkerException(String errorMsg) {
        super("freemarker.core.exception",errorMsg);
    }

    public FreeMarkerException(Object... messageArguments) {
        super("freemarker.core.exception", messageArguments);
    }

    public FreeMarkerException(String message, Object... messageArguments) {
        super("freemarker.core.exception", message, messageArguments);
    }

    public FreeMarkerException(String message, Throwable cause, Object... messageArguments) {
        super("freemarker.core.exception", message, cause, messageArguments);
    }

    public FreeMarkerException(Throwable cause, Object... messageArguments) {
        super("freemarker.core.exception", cause, messageArguments);
    }
}
