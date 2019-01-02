package org.spring.freemarker.ext.validation;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.spring.freemarker.common.exception.FreeMarkerException;
import org.spring.freemarker.common.utils.AssertUtils;
import org.spring.freemarker.common.utils.WebUtils;
import org.spring.freemarker.ext.validation.model.ValidationModel;
import org.spring.freemarker.ext.validation.model.ValidationResult;
import org.spring.freemarker.web.response.ResponseResult;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import org.spring.freemarker.ext.validation.model.Error;

import javax.inject.Inject;
import java.util.List;

/**
 * 验证拦截器；
 *
 * @date 2018-12-13 13:20:43
 */
public class ValidationInterceptor implements MethodInterceptor {

    @Inject
    private Validator validator;

    @Inject
    private MessageSource messageSource;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object[] arguments = methodInvocation.getArguments();
        String methodName = methodInvocation.getMethod().getName();
        AssertUtils.nullArray(arguments,new FreeMarkerException(String.format("the arguments of method '%s' validation is required",methodName)));

        Object validationModel = null;
        BindingResult bindingResult = null;
        for(Object obj : arguments){
            if(validationModel != null && bindingResult != null){
                break;
            }

            //验证入参获取
            if(obj instanceof ValidationModel){
              validationModel = obj;
            }else if(obj instanceof BindingResult){
              bindingResult = (BindingResult)obj;
            }
        }

        if(validationModel == null || bindingResult == null){
            throw new FreeMarkerException(String.format("the arguments of method '%s' validation must contain ValidationModel and BindingResult",methodName));
        }

        this.validator.validate(validationModel,bindingResult);
        Object proceedResult = methodInvocation.proceed();
        if(bindingResult.hasErrors()){

            //针对模板验证
            if(proceedResult instanceof ModelAndView){
                ModelAndView modelAndView = (ModelAndView)proceedResult;
                String validModelName = StringUtils.uncapitalize(validationModel.getClass().getSimpleName());
                modelAndView.getModel().put(String.format("validation_%s_result",validModelName),this.analysisValidateDatas(bindingResult));

                //针对responseBody的验证
            }else if(proceedResult instanceof ResponseResult){
                ResponseResult responseResult = (ResponseResult)proceedResult;
                responseResult.setSuccess(false);
                responseResult.setValidationResult(this.analysisValidateDatas(bindingResult));
            }else{
                throw new FreeMarkerException(String.format("validation method result type must be ModelAndView or ResponseResult,but find type is '%s'",proceedResult.getClass().getName()));
            }
        }

        return proceedResult;
    }

    /**
     * 解析验证错误。
     * @param bindingResult
     * @return
     */
    private ValidationResult analysisValidateDatas(BindingResult bindingResult) {
        ValidationResult validationResult = new ValidationResult();
        List<ObjectError> errors = bindingResult.getAllErrors();

        //封装验证错误
        for (ObjectError error : errors) {
            String errorMsg = this.messageSource.getMessage(error, RequestContextUtils.getLocale(WebUtils.getHttpRequest()));
            errorMsg = StringUtils.isNotEmpty(errorMsg) ? errorMsg:error.getDefaultMessage();
            if (error instanceof FieldError) {
                validationResult.addError(new Error(((FieldError) error).getField(), errorMsg));
            } else {
                validationResult.addError(new Error(error.getObjectName(), errorMsg, false));
            }
        }
        return validationResult;
    }
}
