package org.spring.freemarker.ext.directives.model;

import freemarker.template.TemplateDirectiveBody;
import org.spring.freemarker.cache.CacheElement;

/**
 * 模板缓存，实现对非解析的freemarker的TemplateDirectiveBody进行缓存。
 *
 * @date 2018-12-12 15:56:30
 */
public class TemplateBodyCacheElement implements CacheElement<TemplateDirectiveBody> {

    private static final long serialVersionUID = 8145009478523483255L;

    private TemplateDirectiveBody body;

    public TemplateBodyCacheElement(TemplateDirectiveBody body){
        this.body = body;
    }

    @Override
    public TemplateDirectiveBody getValue() {
        return body;
    }
}
