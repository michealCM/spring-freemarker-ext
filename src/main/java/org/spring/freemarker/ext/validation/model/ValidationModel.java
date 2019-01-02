package org.spring.freemarker.ext.validation.model;

import java.io.Serializable;

/**
 *  需要进行validator验证的model基类，所有的需要验证的Vo都需要继承该类否则在{@link org.spring.freemarker.ext.validation.ValidationInterceptor}；
 *  将获取不到验证必要入参出现失败。
 *
 * @date 2018-12-14 14:46:55
 */
public class ValidationModel implements Serializable {

    private static final long serialVersionUID = 2798169923618374811L;

}
