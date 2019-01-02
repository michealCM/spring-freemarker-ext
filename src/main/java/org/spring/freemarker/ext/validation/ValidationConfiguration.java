package org.spring.freemarker.ext.validation;

import org.apache.commons.lang3.ArrayUtils;
import org.spring.freemarker.common.exception.FreeMarkerException;
import org.spring.freemarker.common.utils.AssertUtils;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.springmodules.validation.commons.DefaultBeanValidator;
import org.springmodules.validation.commons.DefaultValidatorFactory;

/**
 * 验证配置。
 *
 * @date 2018-12-16 18:37:04
 */
@Configuration
public class ValidationConfiguration {

    //是否按照路径拦截
    private boolean annotationValid;

    //类路径拦截规则
    private String patternMatchClassPath;

    //aspect拦截规则
    private String aspectJExpressionString;

    /**
     * 验证规则。
     * classpath*:/validation/validator-rules.xml
     */
    private Resource ruleResource;

    /**
     * 具体的验证配置文件位置，
     * 实例：
     *  classpath*:/validation/validation-*.xml
     */
    private Resource[] validationResources;

    @Bean
    public DefaultValidatorFactory defaultValidatorFactory(){
        DefaultValidatorFactory defaultValidatorFactory = new DefaultValidatorFactory();
        AssertUtils.isNull(ruleResource,new FreeMarkerException("ruleResource not null"));
        AssertUtils.nullArray(validationResources,new FreeMarkerException("validationResources not null"));
        Resource[] allConfig = ArrayUtils.addAll(validationResources, ruleResource);
        defaultValidatorFactory.setValidationConfigLocations(allConfig);
        return defaultValidatorFactory;
    }

    @Bean
    public DefaultBeanValidator defaultBeanValidator(){
        DefaultBeanValidator defaultBeanValidator = new DefaultBeanValidator();
        defaultBeanValidator.setUseFullyQualifiedClassName(false);
        defaultBeanValidator.setValidatorFactory(defaultValidatorFactory());
        return defaultBeanValidator;
    }

    @Bean
    public ValidationInterceptor validationInterceptor(){
        return new ValidationInterceptor();
    }

    @Bean
    public ValidationPointcutAdvisor validationPointcutAdvisor(){

        if(!StringUtils.isEmpty(patternMatchClassPath)){
            return new ValidationPointcutAdvisor(validationInterceptor(),patternMatchClassPath);
        }

        if(!StringUtils.isEmpty(aspectJExpressionString)){
            AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
            aspectJExpressionPointcut.setExpression(aspectJExpressionString);
            return new ValidationPointcutAdvisor(validationInterceptor(),aspectJExpressionPointcut);
        }

        return new ValidationPointcutAdvisor(validationInterceptor(),annotationValid);
    }

    public void setAnnotationValid(boolean annotationValid) {
        this.annotationValid = annotationValid;
    }

    public void setPatternMatchClassPath(String patternMatchClassPath) {
        this.patternMatchClassPath = patternMatchClassPath;
    }

    public void setAspectJExpressionString(String aspectJExpressionString) {
        this.aspectJExpressionString = aspectJExpressionString;
    }

    public void setValidationResources(Resource[] validationResources) {
        this.validationResources = validationResources;
    }

    public void setRuleResource(Resource ruleResource) {
        this.ruleResource = ruleResource;
    }
}
