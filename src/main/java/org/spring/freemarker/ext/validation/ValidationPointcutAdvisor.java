package org.spring.freemarker.ext.validation;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.util.PatternMatchUtils;

import java.lang.reflect.Method;

/**
 *
 *
 * @date 2018-12-16 16:42:15
 */
public class ValidationPointcutAdvisor extends DefaultPointcutAdvisor {

    private static final long serialVersionUID = -8557168413007929742L;

    //默认对类路径进行拦截
    private static final String DEFAULT_PATTERN_CLASS_PATH = "*.controller.*";

    /**
     * isPatternClassPath为true是按照默认配置路径拦截；false通过判断是否被{@link Validator}标签注解。
     * @param validationInterceptor
     * @param isPatternClassPath
     */
    public ValidationPointcutAdvisor(ValidationInterceptor validationInterceptor,final boolean isAnnotationValid){
        super(new StaticMethodMatcherPointcut() {
            @Override
            public boolean matches(Method method, Class<?> targetClass) {

                //判断是否按照Validator注解进行拦截匹配
                if(isAnnotationValid){
                    return method.getAnnotation(Validator.class)!= null;
                }else{
                    return PatternMatchUtils.simpleMatch(DEFAULT_PATTERN_CLASS_PATH,targetClass.getName());
                }
            }
        }, validationInterceptor);
    }

    /**
     * 根据配置路径进行验证拦截。
     * @param validationInterceptor
     * @param patternMatchClassPath
     */
    public ValidationPointcutAdvisor(ValidationInterceptor validationInterceptor,final String patternMatchClassPath){
        super(new StaticMethodMatcherPointcut() {
            @Override
            public boolean matches(Method method, Class<?> targetClass) {
                return PatternMatchUtils.simpleMatch(patternMatchClassPath,targetClass.getName());
            }
        }, validationInterceptor);
    }

    /**
     * 根据自定义AspectJExpressionPointcut进行拦截。
     * @param validationInterceptor
     * @param aspectJExpressionPointcut
     */
    public ValidationPointcutAdvisor(ValidationInterceptor validationInterceptor,
                                     AspectJExpressionPointcut aspectJExpressionPointcut){
        super(aspectJExpressionPointcut, validationInterceptor);
    }
}
