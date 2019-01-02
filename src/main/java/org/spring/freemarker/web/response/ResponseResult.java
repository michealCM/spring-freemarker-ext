package org.spring.freemarker.web.response;

import org.spring.freemarker.ext.validation.model.ValidationResult;

import java.io.Serializable;

/**
 * 自定义respopnseBody的统一返回。
 *
 * @date 2018-12-14 15:40:58
 * @param <T>
 */
public class ResponseResult <T> implements Serializable {

    private static final long serialVersionUID = 7103481965152670586L;

    //是否成功
    private boolean success;

    //状态码
    private int statusCode;

    //消息
    private String message;

    //牵涉验证的返回消息。
    private ValidationResult validationResult;

    //具体的数据
    private T resultData;

    public ResponseResult(boolean success){
        this.success = success;
    }

    public ResponseResult(boolean success, int statusCode){
        this.success = success;
        this.statusCode = statusCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ValidationResult getValidationResult() {
        return validationResult;
    }

    public void setValidationResult(ValidationResult validationResult) {
        this.validationResult = validationResult;
    }

    public T getResultData() {
        return resultData;
    }

    public void setResultData(T resultData) {
        this.resultData = resultData;
    }
}
