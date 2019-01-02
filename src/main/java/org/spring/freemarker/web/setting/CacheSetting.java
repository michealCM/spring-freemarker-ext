package org.spring.freemarker.web.setting;

import java.io.Serializable;

/**
 * 缓存配置（缓存类型，模板缓存名称，页面缓存名称）。
 *
 *   不同的缓存类型，模板缓存名称，页面缓存名称对应含义不同；
 *   cacheProvidor         templateCache                pageCache
 *    ehcahche      对应ehcache.xml的缓存节点      对应ehcache.xml的缓存节点
 *    redis         使用该字符作为模板缓存的前缀    使用该字符作为页面缓存的前缀
 *
 * @date 2018-12-25 11:13:58
 */
public class CacheSetting implements Serializable {

    private static final long serialVersionUID = 3468421041775543902L;

    private String cacheProvidor;

    private String templateCache;

    private String pageCache;

    public String getCacheProvidor() {
        return cacheProvidor;
    }

    public void setCacheProvidor(String cacheProvidor) {
        this.cacheProvidor = cacheProvidor;
    }

    public String getTemplateCache() {
        return templateCache;
    }

    public void setTemplateCache(String templateCache) {
        this.templateCache = templateCache;
    }

    public String getPageCache() {
        return pageCache;
    }

    public void setPageCache(String pageCache) {
        this.pageCache = pageCache;
    }
}
