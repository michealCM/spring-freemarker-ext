package org.spring.freemarker.cache.pageCache.impl;

import org.spring.freemarker.cache.CacheElement;
import org.spring.freemarker.cache.pageCache.ServletResponseInfo;

/**
 * 实现的页面缓存，对response的读取的存储；{@link ServletResponseInfo}。
 *
 * @date 2018-12-12 15:30:47
 */
public class PageCacheElement implements CacheElement<ServletResponseInfo> {

    private static final long serialVersionUID = 6319065265310000456L;

    //reponse返回的页面数据
    private ServletResponseInfo servletResponseInfo;

    public PageCacheElement(ServletResponseInfo servletResponseInfo){
        this.servletResponseInfo = servletResponseInfo;
    }

    @Override
    public ServletResponseInfo getValue() {
        return servletResponseInfo;
    }
}
