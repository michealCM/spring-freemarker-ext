package org.spring.freemarker.web.event.listener;

import org.spring.freemarker.common.exception.FreeMarkerException;
import org.spring.freemarker.common.utils.AssertUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 *
 * @date 2018-11-29 17:08:23
 */
public class ApplicationContextHelper {

    private static ApplicationContext applicationContext;

    public static final void initApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

    /**
     * 根据根据beanName获取Spring容器中管理的bean。
     * @param beanName
     * @return
     */
    public static final Object getSpringBean(String beanName){
        Object springBean = applicationContext.getBean(beanName);
        AssertUtils.isNull(springBean,new FreeMarkerException(String.format("bean name of %s not exist in Spring ",beanName)));
        return springBean;
    }

    /**
     * 根据类型获取Spring容器中管理的bean。
     * @param clazz
     * @param <T>
     * @return
     */
    public static final <T> T getSpringBean(Class<T> clazz){
        T springBean = applicationContext.getBean(clazz);
        AssertUtils.isNull(springBean,new FreeMarkerException(String.format("class type of %s not exist in Spring ",clazz.getSimpleName())));
        return springBean;
    }

    /**
     * 获取资源。
     * @param locationPattern
     * @return
     * @throws IOException
     */
    public static Resource[] getResources(String locationPattern) throws IOException {
        AssertUtils.isBlank(locationPattern,new FreeMarkerException("'getResources' parameter of locationPattern not null and empty"));
        return applicationContext.getResources(locationPattern);
    }
}
