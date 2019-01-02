package org.spring.freemarker.cache.pageCache.impl;

import org.apache.commons.lang3.StringUtils;
import org.spring.freemarker.cache.pageCache.AbstractCacheServletResponseFilter;
import org.spring.freemarker.common.utils.TimeLength;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 继承缓存过滤器{@link AbstractCacheServletResponseFilter}，实现缓存过期失效时间getCacheTime方法；
 * 并实现自定义的缓存生效策略（通过访问匹配访问路径实现）；通过web.xml中配置filter进行初始化参数配置。
 *
 *               类型     是否必填           描述
 * cacheUrl     String      是            页面缓存地址，多个使用英文逗号间隔（去掉项目名称的相对路径）
 * cacheTime    int         否（默认2h）   缓存失效时间（单位：小时）
 *
 * 实例：
 *     <filter>
 *         <filter-name>pageCacheFilter</filter-name>
 *         <filter-class>org.spring.freemarker.cache.pageCache.impl.CacheResponseFilter</filter-class>
 *         <init-param>
 *            <param-name>cacheUrl</param-name>
 *            <param-value>/home.html,/common/common.html</param-value>
 *         </init-param>
 *     </filter>
 *     <filter-mapping>
 *         <filter-name>pageCacheFilter</filter-name>
 *         <url-pattern>*.html</url-pattern>
 *     </filter-mapping>
 *
 * @date 2018-12-11 11:57:53
 */
public class CacheResponseFilter extends AbstractCacheServletResponseFilter {

    //需要进行缓存的地址数组
    private String[] cacheRequestURIArray = null;

    //缓存失效时间
    private String cacheTime;

    @Override
    protected TimeLength getCacheTime() {
        if(StringUtils.isBlank(cacheTime)){
            return TimeLength.hours(2);
        }

        return TimeLength.hours(Integer.parseInt(cacheTime));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest)request;
        HttpServletResponse servletResponse = (HttpServletResponse)response;
        String requestURL = this.analysisRequestCacheUrl(servletRequest);

        if(requestURL == null){
            chain.doFilter(servletRequest,servletResponse);
        }else{
            super.doFilter(request, response, chain);
        }
    }

    private String analysisRequestCacheUrl(HttpServletRequest servletRequest){
        if(cacheRequestURIArray != null){
        	String requestURI = servletRequest.getRequestURI().replaceFirst(servletRequest.getContextPath(), "");
            for(String url : cacheRequestURIArray){
                if(url.equals(requestURI)){
                    return url;
                }
            }
        }
        return null;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String cacheRequestURI = filterConfig.getInitParameter("cacheUrl");
        if(StringUtils.isNotBlank(cacheRequestURI)){
            cacheRequestURIArray = cacheRequestURI.split(",");
        }

        cacheTime = filterConfig.getInitParameter("cacheTime");
    }

    @Override
    public void destroy() {
    }
}
