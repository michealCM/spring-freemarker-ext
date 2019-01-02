package org.spring.freemarker.ext.directives.model;

import org.spring.freemarker.cache.CacheElement;

/**
 * 模板缓存，针对模板已经被freemarker解析了的html页面信息。
 *
 * @date 2018-12-12 15:58:44
 */
public class TemplateContentCacheElement implements CacheElement<String> {

    private static final long serialVersionUID = 8346816813687468690L;

    //解析了的html页面子串
    private String tempalteContent;

    public TemplateContentCacheElement(String tempalteContent){
        this.tempalteContent = tempalteContent;
    }

    @Override
    public String getValue() {
        return tempalteContent;
    }
}
