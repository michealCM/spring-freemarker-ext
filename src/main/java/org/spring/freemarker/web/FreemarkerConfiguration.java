package org.spring.freemarker.web;

import org.spring.freemarker.cache.CacheType;
import org.spring.freemarker.cache.ICacheRepository;
import org.spring.freemarker.cache.providor.EhcacheRepository;
import org.spring.freemarker.cache.providor.RedisCacheRepository;
import org.spring.freemarker.common.exception.FreeMarkerException;
import org.spring.freemarker.ext.context.RequestModelContextHolder;
import org.spring.freemarker.ext.context.TemplateDirectiveBodyContextHolder;
import org.spring.freemarker.ext.directives.DirectiveConfiguration;
import org.spring.freemarker.ext.functions.FunctionConfiguration;
import org.spring.freemarker.web.event.listener.ApplicationContextRefreshedListener;
import org.spring.freemarker.web.setting.CDNSetting;
import org.spring.freemarker.web.setting.CacheSetting;
import org.springframework.context.annotation.*;
import org.springframework.web.context.WebApplicationContext;

/**
 * 自定义freemarker配置启动类，负责对整个项目的初始化工作。
 *
 * @date 2018-11-26 14:28:41
 */
@Configuration
@Import({DirectiveConfiguration.class,FunctionConfiguration.class})
public class FreemarkerConfiguration extends PropertiesConfiguration{

    @Bean
    public ApplicationContextRefreshedListener applicationContextRefreshedListener(){
        ApplicationContextRefreshedListener applicationContextRefreshedListener = new ApplicationContextRefreshedListener();
        return applicationContextRefreshedListener;
    }

    @Bean
    public RequestModelContextHolder requestModelContextHolder(){
        RequestModelContextHolder requestModelContextHolder = new RequestModelContextHolder();
        return requestModelContextHolder;
    }

    @Bean
    @Scope(value=WebApplicationContext.SCOPE_REQUEST,proxyMode=ScopedProxyMode.TARGET_CLASS)
    public TemplateDirectiveBodyContextHolder templateDirectiveBodyContextHolder(){
        TemplateDirectiveBodyContextHolder templateDirectiveBodyContextHolder = new TemplateDirectiveBodyContextHolder();
        return templateDirectiveBodyContextHolder;
    }

    @Bean
    public CacheSetting cacheSetting(){
        CacheSetting cacheSetting = new CacheSetting();
        cacheSetting.setCacheProvidor(getCacheProvidor());
        cacheSetting.setPageCache(getPageCache()==null?"":getPageCache());
        cacheSetting.setTemplateCache(getTemplateCache()==null?"":getTemplateCache());
        return cacheSetting;
    }

    @Bean
    public CDNSetting cdnSetting(){
        CDNSetting cdnSetting = new CDNSetting();
        cdnSetting.setVersion(getVersion());
        cdnSetting.setCDNServer(getCDNServer()==null?"":getCDNServer());
        cdnSetting.setDefaultImage(getDefaultImage()==null?"":getDefaultImage());
        cdnSetting.setLoadingImage(getLoadingImage()==null?"":getLoadingImage());
        cdnSetting.setLoadingErrorImage(getLoadingErrorImage()==null?"":getLoadingErrorImage());
        return cdnSetting;
    }

    @Bean
    public ICacheRepository cacheRepository() throws FreeMarkerException{
        if(getCacheProvidor().equals(CacheType.CACHE_TYPE_EHCACHE.getValue())){
            return new EhcacheRepository();
        }else if(getCacheProvidor().equals(CacheType.CACHE_TYPE_REDIS.getValue())){
            return new RedisCacheRepository();
        }

        throw new FreeMarkerException("not valid cache and the cache must be 'ehcache' or 'redis'");
    }
}
