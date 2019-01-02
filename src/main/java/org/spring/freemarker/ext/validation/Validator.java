package org.spring.freemarker.ext.validation;

import java.lang.annotation.*;

/**
 * 自定义需要进行验证的标签；
 *
 * @date 2018-12-16 16:34:04
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Validator {

}
