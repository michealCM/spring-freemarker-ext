package org.spring.freemarker.ext.context;

import org.spring.freemarker.ext.directives.impl.CacheTemplateDirective;

import java.util.Map;

/**
 * 对每一次的httpRequest请求的参数绑定，便于后期的一些自定义标签获取解析值，进行模板解析；
 * 具体可以参考{@link CacheTemplateDirective}
 *
 * @date 2018-11-23 15:17:22
 */
public final class RequestModelContextHolder {

    private final ThreadLocal<Map<String, Object>> requestModelContext = new ThreadLocal<Map<String, Object>>();

    public Map<String, Object> getModel() {
        return requestModelContext.get();
    }

    public void bindModel(Map<String, Object> model) {
        requestModelContext.set(model);
    }

    public void releaseModel() {
        requestModelContext.remove();
    }

}
