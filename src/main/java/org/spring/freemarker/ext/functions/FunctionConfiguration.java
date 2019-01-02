package org.spring.freemarker.ext.functions;

import org.spring.freemarker.ext.functions.impl.NumberFormatFunction;
import org.spring.freemarker.ext.functions.impl.URLFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义函数统一配置管理，最后交由spring容器进行统一管理。
 *
 * @date 2018-11-29 16:26:42
 */
@Configuration
public class FunctionConfiguration {

    @Bean
    public NumberFormatFunction numberFormatFunction(){
        return new NumberFormatFunction();
    }

    @Bean
    public URLFunction urlFunction(){
        return new URLFunction();
    }
}
