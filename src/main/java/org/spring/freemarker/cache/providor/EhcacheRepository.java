package org.spring.freemarker.cache.providor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.freemarker.cache.CacheElement;
import org.spring.freemarker.cache.CacheKey;
import org.spring.freemarker.cache.ICacheRepository;
import org.spring.freemarker.common.utils.TimeLength;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.support.SimpleValueWrapper;

import javax.inject.Inject;

/**
 * 自定义实现的ehcahce缓存实现；此类只针对注入的{EhCacheCacheManager进行ehCache的缓存操作，
 * 针对EhCacheCacheManager的实例和配置初始化需要在项目中进行具体的配置，
 * 具体详见{@link EhCacheCacheManager}的配置。
 *
 * @date 2018-12-4 16:49:20
 */
public class EhcacheRepository implements ICacheRepository {

    private static Logger logger = LoggerFactory.getLogger(EhcacheRepository.class);

    @Inject
    private EhCacheCacheManager ehCacheCacheManager;

    @Override
    public boolean setCache(CacheKey key, CacheElement value) {
        Cache cache = this.ehCacheCacheManager.getCache(key.getCacheName());
        cache.put(key.getKey(),value);
        return true;
    }

    /**
     * 针对ehcache不支持动态设置缓存失效时间。
     * @param key
     * @param value
     * @param expire
     * @return
     */
    @Override
    @Deprecated
    public boolean setCache(CacheKey key, CacheElement value, TimeLength expire) {
        logger.warn("Ehcache not support set expireTime of cache!");
        return this.setCache(key,value);
    }

    @Override
    public CacheElement loadCache(CacheKey key, TimeLength expire) {
        logger.warn("Ehcache not support when loadCache to refresh expireTime of cache!");

        Cache cache = this.ehCacheCacheManager.getCache(key.getCacheName());
        SimpleValueWrapper valueWrapper = (SimpleValueWrapper)cache.get(key.getKey());
        if(valueWrapper != null){
            return (CacheElement)valueWrapper.get();
        }

        return null;
    }

    @Override
    public boolean deleteCache(CacheKey key) {
        Cache cache = this.ehCacheCacheManager.getCache(key.getCacheName());
        cache.evict(key.getKey());
        return true;
    }

    @Override
    public boolean clear(CacheKey key) {
        Cache cache = this.ehCacheCacheManager.getCache(key.getCacheName());
        cache.clear();
        return true;
    }
}
