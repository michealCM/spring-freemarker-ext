package org.spring.freemarker.cache.providor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.freemarker.cache.CacheElement;
import org.spring.freemarker.cache.CacheKey;
import org.spring.freemarker.cache.ICacheRepository;
import org.spring.freemarker.common.utils.TimeLength;
import org.springframework.data.redis.core.RedisTemplate;

import javax.inject.Inject;
import java.util.Set;

/**
 * 自定义实现的redis缓存方案，提供redis的缓存操作；功能依赖于spring-data-redis提供的redis操作；
 * 对redis的操作具体参照{@link RedisTemplate}。
 *
 * @date 2018-12-4 17:36:29
 */
public class RedisCacheRepository implements ICacheRepository {

    private static Logger logger = LoggerFactory.getLogger(RedisCacheRepository.class);
    private static final String REDIS_COMMON_KEY_SUFFIX  = "*";

    @Inject
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 根据CacheKey组装Redis 的字符Key；
     * @param key
     * @return
     */
    private String buildRedisKey(CacheKey key){
        return key.getCacheName()+"_"+key.getKey();
    }

    @Override
    public boolean setCache(CacheKey key, CacheElement value) {
        this.redisTemplate.opsForValue().set(this.buildRedisKey(key),value);
        return true;
    }

    @Override
    public boolean setCache(CacheKey key, CacheElement value, TimeLength expire) {
        logger.info("redis support set expireTime of cache!");
        this.redisTemplate.opsForValue().set(this.buildRedisKey(key),value,expire.length(),expire.unit());
        return true;
    }

    @Override
    public CacheElement loadCache(CacheKey key, TimeLength expire) {
        CacheElement cacheValue = (CacheElement)this.redisTemplate.opsForValue().get(this.buildRedisKey(key));
        if(cacheValue != null){
            this.redisTemplate.expire(this.buildRedisKey(key),expire.length(),expire.unit());
            return cacheValue;
        }

       return null;
    }

    @Override
    public boolean deleteCache(CacheKey key) {
        this.redisTemplate.delete(this.buildRedisKey(key));
        return true;
    }

    @Override
    public boolean clear(CacheKey key) {
        Set<String> keys = this.redisTemplate.keys(key.getCacheName()+REDIS_COMMON_KEY_SUFFIX);
        this.redisTemplate.delete(keys);
        return true;
    }
}
