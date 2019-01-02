package org.spring.freemarker.cache.pageCache;

import org.spring.freemarker.cache.CacheKey;
import org.spring.freemarker.cache.ICacheRepository;
import org.spring.freemarker.cache.pageCache.impl.PageCacheElement;
import org.spring.freemarker.common.utils.TimeLength;
import org.spring.freemarker.web.event.listener.ApplicationContextHelper;
import org.spring.freemarker.web.setting.CacheSetting;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.TreeSet;

/**
 * 缓存过滤器，负责改写HttpServletResponse；通过重新构造一个继承{@link HttpServletResponseWrapper}来获取response返回的页面数据；
 * 实现对页面的缓存，提供抽象的getCacheTime方法获取缓存失效时间。
 *
 * @date 2018-11-23 11:35:43
 */
public abstract class AbstractCacheServletResponseFilter implements Filter {

    private static final String DEFAULT_PAGE_CACHE_PREFFIX = "page-cache:";

    /**
     * 获取缓存失效时间。
     * @return
     */
    protected abstract TimeLength getCacheTime();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest)request;
        HttpServletResponse servletResponse = (HttpServletResponse)response;

        String pageCacheKey = this.buildPageCacheKey(servletRequest);
        ICacheRepository cacheRepository = ApplicationContextHelper.getSpringBean(ICacheRepository.class);
        CacheSetting cacheSetting = ApplicationContextHelper.getSpringBean(CacheSetting.class);

        CacheKey cacheKey = new CacheKey(pageCacheKey,cacheSetting.getPageCache());
        PageCacheElement pageCacheElement = (PageCacheElement)cacheRepository.loadCache(cacheKey,getCacheTime());
        ServletResponseInfo servletResponseInfo = null;

        if(pageCacheElement == null){
            CacheServletResponseWrapper cacheServletResponseWrapper = new CacheServletResponseWrapper(servletResponse);
            chain.doFilter(servletRequest,cacheServletResponseWrapper);

            if(cacheServletResponseWrapper.isOk()){
                servletResponseInfo = new ServletResponseInfo(cacheServletResponseWrapper.getStatus(),
                        cacheServletResponseWrapper.getContentType(),cacheServletResponseWrapper.getContent(),
                        cacheServletResponseWrapper.getAllHeaders());

                cacheRepository.setCache(cacheKey,new PageCacheElement(servletResponseInfo),getCacheTime());
            }
        }else{
            servletResponseInfo = pageCacheElement.getValue();
        }

        setStatus(servletResponse, servletResponseInfo);
        setContentType(servletResponse, servletResponseInfo);
        setHeaders(servletResponse, servletResponseInfo);
        setContentLength(servletResponse, servletResponseInfo);

        ServletOutputStream servletOutputStream = servletResponse.getOutputStream();
        servletOutputStream.write(servletResponseInfo.getResponseBody());
        servletOutputStream.flush();
    }

    /**
     * 获取访问路径地址，添加通用前缀构建页面缓存主键。
     * @param httpRequest
     * @return
     */
    private String buildPageCacheKey(HttpServletRequest httpRequest) {
        StringBuffer cacheKey = new StringBuffer(DEFAULT_PAGE_CACHE_PREFFIX);
        String requestURI = httpRequest.getRequestURI().replaceFirst(httpRequest.getContextPath(), "");
        cacheKey.append(httpRequest.getMethod()).append(requestURI);
        if("/".equals(requestURI)) {
            cacheKey.append("index.html");
        }
        return cacheKey.toString();
    }

    private void setContentLength(HttpServletResponse response, ServletResponseInfo servletResponseInfo) {
        response.setContentLength(servletResponseInfo.getResponseBody().length);
    }

    private void setContentType(HttpServletResponse response, ServletResponseInfo servletResponseInfo) {
        String contentType = servletResponseInfo.getContentType();
        if (contentType != null && contentType.length() > 0) {
            response.setContentType(contentType);
        }
    }

    private void setStatus(HttpServletResponse response,ServletResponseInfo servletResponseInfo) {
        response.setStatus(servletResponseInfo.getStatusCode());
    }

    private void setHeaders(final HttpServletResponse response,final ServletResponseInfo servletResponseInfo) {
        final Collection<Header<? extends Serializable>> headers = servletResponseInfo.getHeaders();
        final TreeSet<String> setHeaders = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        for (final Header<? extends Serializable> header : headers) {
            final String name = header.getName();
            switch (header.getType()) {
                case STRING:
                    if (setHeaders.contains(name)) {
                        response.addHeader(name, (String) header.getValue());
                    } else {
                        setHeaders.add(name);
                        response.setHeader(name, (String) header.getValue());
                    }
                    break;
                case DATE:
                    if (setHeaders.contains(name)) {
                        response.addDateHeader(name, (Long) header.getValue());
                    } else {
                        setHeaders.add(name);
                        response.setDateHeader(name, (Long) header.getValue());
                    }
                    break;
                case INT:
                    if (setHeaders.contains(name)) {
                        response.addIntHeader(name, (Integer) header.getValue());
                    } else {
                        setHeaders.add(name);
                        response.setIntHeader(name, (Integer) header.getValue());
                    }
                    break;
                default:
                    throw new IllegalArgumentException("No mapping for Header: " + header);
            }
        }
    }
}
